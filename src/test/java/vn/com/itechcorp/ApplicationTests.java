package vn.com.itechcorp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.com.itechcorp.base.api.response.APIListResponse;
import vn.com.itechcorp.base.service.filter.PageOfData;
import vn.com.itechcorp.base.service.filter.PaginationInfo;
import vn.com.itechcorp.module.diagnosis.DiagnosisMethod;
import vn.com.itechcorp.module.modality.dto.ModalityDTOGet;
import vn.com.itechcorp.module.modality.model.Modality;
import vn.com.itechcorp.module.modality.repository.ModalityRepository;
import vn.com.itechcorp.module.procedure.dto.ProcedureSiteFilter;
import vn.com.itechcorp.module.procedure.model.Procedure;
import vn.com.itechcorp.module.procedure.repository.ProcedureRepository;
import vn.com.itechcorp.module.procedure.service.ProcedureService;
import vn.com.itechcorp.module.site.repository.SiteRepository;
import vn.com.itechcorp.module.speaker.dto.SoundDtoGet;
import vn.com.itechcorp.module.speaker.dto.SoundFilter;
import vn.com.itechcorp.module.speaker.service.SoundService;
import vn.com.itechcorp.module.ticket.dto.TicketDTOGet;
import vn.com.itechcorp.module.ticket.model.Ticket;
import vn.com.itechcorp.module.ticket.model.TicketCount;
import vn.com.itechcorp.module.ticket.repository.TicketRepository;
import vn.com.itechcorp.module.ticket.service.TicketScheduler;
import vn.com.itechcorp.ris.proxy.AuthProxy;
import vn.com.itechcorp.ris.proxy.RisProxy;
import vn.com.itechcorp.ris.dto.BasicAuth;
import vn.com.itechcorp.ris.dto.OrderDTOGet;
import vn.com.itechcorp.ris.dto.OrderFilter;
import vn.com.itechcorp.ris.dto.UserTokenDTOGet;
import vn.com.itechcorp.util.PaginationUtil;
import vn.com.itechcorp.util.SoundUtil;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private ModalityRepository modalityRepository;

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ProcedureService procedureService;

    @Autowired
    private SoundService soundService;

    @Autowired
    private AuthProxy authProxy;

    @Autowired
    private RisProxy risProxy;

    @Test
    void contextLoads() {
    }

    Modality getFreeModality(Set<Modality> modalities, Map<Long, TicketCount> ticketCounts) {
        Modality result = null;
        long count = 999999999; // very large number
        for (Modality modality : modalities) {
            TicketCount ticketCount = ticketCounts.get(modality.getId());
            if (ticketCount == null) return modality;
            if (ticketCount.getTotal() < count) {
                count = ticketCount.getTotal();
                result = modality;
            }
        }
        return result;
    }

    Map.Entry<Modality, List<Procedure>> findSuitableModality(Map<Modality, List<Procedure>> modalities, List<Long> procedureIds, Map<Long, TicketCount> ticketCounts) {
        // Find the ones which support largest number of procedures
        Map<Modality, List<Procedure>> filteredModalities = new HashMap<>();
        for (Map.Entry<Modality, List<Procedure>> m : modalities.entrySet()) {
            // Get the list of mutual procedures
            List<Procedure> supportedProcedures = m.getValue().stream().filter(p -> procedureIds.contains(p.getId())).collect(Collectors.toList());
            filteredModalities.put(m.getKey(), supportedProcedures);
        }

        int maxSupportedProcedures = modalities.values().stream().mapToInt(v -> v.size()).max().orElse(0);
        Set<Modality> selectedModalities = modalities.entrySet().stream().filter(x -> x.getValue().size() == maxSupportedProcedures).map(x -> x.getKey()).collect(Collectors.toSet());

        // Find the one which is the least busiest.
        Modality modality = getFreeModality(selectedModalities, ticketCounts);

        return filteredModalities.entrySet().stream().filter(m -> m.getKey().getId() == modality.getId()).findFirst().orElse(null);
    }

    @Test
    void test1() {
        List<Long> procedureIds = new ArrayList<Long>() {{
            add(1L);
            add(2L);
            add(3L);
            add(4L);
        }};
        List<Procedure> procedures = procedureRepository.findAllById(procedureIds);
        Map<Modality, List<Procedure>> modalities = procedures.stream().flatMap(p -> p.getModalities().stream().map(m -> Pair.of(m, p)))
                .collect(Collectors.groupingBy(Pair::getKey, Collectors.mapping(Pair::getValue, Collectors.toList())));

        // If modalities == null || modalities.isEmpty() return;
        // If modalities.size() == 1 return modalities.get(0);

        // Filter list of supported procedures by modality
        Set<Procedure> supportedProcedures = modalities.entrySet().stream().flatMap(m -> m.getValue().stream()).collect(Collectors.toSet());
        List<Long> supportedProcedureIds = supportedProcedures.stream().map(Procedure::getId).collect(Collectors.toList());

        List<TicketCount> ticketCounts = ticketRepository.countTotalTicketsByModality(modalities.keySet().stream().map(m -> m.getId()).collect(Collectors.toList()));
        Map<Long, TicketCount> ticketCountMap = ticketCounts.stream().collect(Collectors.toMap(TicketCount::getModalityID, Function.identity()));

        while (!supportedProcedureIds.isEmpty() && !modalities.isEmpty()) {
            Map.Entry<Modality, List<Procedure>> suitableModality = findSuitableModality(modalities, supportedProcedureIds, ticketCountMap);
            if (suitableModality == null) break;
            supportedProcedureIds.removeIf(pID -> suitableModality.getValue().stream().anyMatch(p -> p.getId() == pID));
            modalities.remove(suitableModality.getKey());
        }

        // Sort which one supports the most procedures
        System.out.println("def");
    }

    @Test
    void test2() {
        List<Ticket> tickets = ticketRepository.findAllByModalityIDIn(Arrays.asList(1L, 2L));
        Map<Long, Long> totalTickets = tickets.stream().collect(Collectors.groupingBy(Ticket::getModalityID, Collectors.counting()));
        System.out.println("def");
    }

    @Test
    void test3() {
//        List<Ticket> all = ticketRepository.findAllById(Arrays.asList(1L, 2L, 3L, 4L, 5L));
        List<Ticket> tickets = ticketRepository.findAllByModalityIDIn(Arrays.asList(1L, 2L));
        TicketDTOGet ticketDTOGet = new TicketDTOGet(tickets.get(0));
        System.out.println("def");
    }

    @Test
    void test5() {
//        List<Long> procedureIDs = Arrays.asList(1L, 2L, 3L, 4L);
        List<Long> procedureIDs = Arrays.asList(64L);
        Map<Long, ModalityDTOGet> supportedModalities = procedureService.getSupportedModalities(new ProcedureSiteFilter(procedureIDs, 1L));
        System.out.println("def");
    }

    @Test
    void test6() {
        UserTokenDTOGet token = authProxy.getToken(new BasicAuth("hungna", "123456"));

        String pid = "17048399";
        OrderFilter filter= new OrderFilter();
        filter.setPid(pid);
        APIListResponse<List<OrderDTOGet>> listAPIListResponse = risProxy.searchOrder("Bearer " + token.getToken().getAccess_token(), filter);

        System.out.println("def");
    }

    @Autowired
    private TicketScheduler ticketScheduler;

    @Autowired
    private DiagnosisMethod diagnosisMethod;

    @Test
    void test7() {
        ticketScheduler.scan();
        String ticketNumber = "110";
        if (ticketNumber != null && ticketNumber.length() <= 4) {
            String padding = "";
            for (int i = 0; i < 4 - ticketNumber.length(); ++i)
                padding = "0" + padding;
            ticketNumber = padding + ticketNumber;
        }
        System.out.printf("def");
    }

    @Test
    void test8() {
        List<Integer> arr = new ArrayList<>();
        arr.add(0);
        arr.add(1);
        arr.add(3);
        arr.add(4);
        arr.add(5);
        arr.add(6);
        arr.add(7);

        List<Integer> pageOfData = PaginationUtil.getPageOfData(arr, 0, 2);
        System.out.println("def");

    }

    @Autowired
    private SoundUtil soundUtil;

    @Test
    void test9() throws Exception {
        byte[] speak = soundUtil.speak("1234", null);

        File out = new File("/home/phtran/Music/testAudio.wav");
        Files.deleteIfExists(out.toPath());

        try(InputStream in = new ByteArrayInputStream(speak);
            AudioInputStream audio = AudioSystem.getAudioInputStream(in)) {
            AudioSystem.write(audio,
                    AudioFileFormat.Type.WAVE,
                    out);
        }

        System.out.println("def");

    }

    @Test
    void test10() throws Exception {
        byte[] speak = soundUtil.speak("123", null);
        System.out.println("def");

    }

    @Getter @Setter @AllArgsConstructor
    class MyTest {
        private Integer serviceID;

        private String name;
    }

    @Test
    void test11() throws Exception {
        List<MyTest> myTests = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            myTests.add(new MyTest(i, "Name" + i));
        }

        Set<Integer> existingIDs = new HashSet<>();
        existingIDs.add(1);
        existingIDs.add(2);
        existingIDs.add(3);

        myTests.removeIf(test -> existingIDs.contains(test.getServiceID()));
        System.out.println("def");
    }

    @Test
    void testAudio(){
        PageOfData<SoundDtoGet> pod = soundService.getPageOfData(new SoundFilter((long)1), new PaginationInfo());
        List<SoundDtoGet> sounds = pod.getElements();

        SoundDtoGet first = sounds.get(0);
//        byte[] first = sounds.get(0).getAudio();
        try(InputStream in = new ByteArrayInputStream(first.getAudio());
            AudioInputStream audio = AudioSystem.getAudioInputStream(in);){

            File out = new File("/home/phtran/Music/testAudio.wav");
            Files.deleteIfExists(out.toPath());

            AudioSystem.write(audio,
                    AudioFileFormat.Type.WAVE,
                    out);

            System.out.println("created");
        }catch (Exception e){
        }

    }

}

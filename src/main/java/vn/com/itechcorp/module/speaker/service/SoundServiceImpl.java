package vn.com.itechcorp.module.speaker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.itechcorp.base.exception.APIException;
import vn.com.itechcorp.base.exception.InvalidOperationOnObjectException;
import vn.com.itechcorp.base.exception.ObjectNotFoundException;
import vn.com.itechcorp.base.service.dto.BaseDtoCreate;
import vn.com.itechcorp.base.service.filter.BaseFilter;
import vn.com.itechcorp.base.service.filter.PageOfData;
import vn.com.itechcorp.base.service.filter.PaginationInfo;
import vn.com.itechcorp.base.service.impl.BaseDtoJpaServiceImpl;
import vn.com.itechcorp.module.modality.model.Modality;
import vn.com.itechcorp.module.modality.repository.ModalityRepository;
import vn.com.itechcorp.module.speaker.dto.SoundDtoCreate;
import vn.com.itechcorp.module.speaker.dto.SoundDtoGet;
import vn.com.itechcorp.module.speaker.model.Sound;
import vn.com.itechcorp.module.speaker.repository.SoundRepository;
import vn.com.itechcorp.module.ticket.model.Ticket;
import vn.com.itechcorp.module.ticket.repository.TicketRepository;
import vn.com.itechcorp.util.SoundUtil;

import java.util.List;
import java.util.stream.Collectors;

@Service("soundService")
public class SoundServiceImpl extends BaseDtoJpaServiceImpl<SoundDtoGet, Sound, Long> implements SoundService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SoundRepository soundRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ModalityRepository modalityRepository;

    @Autowired
    private AudioResourceService audioResourceService;

    @Override
    public SoundRepository getRepository() {
        return soundRepository;
    }

    @Override
    public SoundDtoGet convert(Sound sound) {
        SoundDtoGet result = new SoundDtoGet(sound);
        result.setAudio(sound.getAudio());
        return result;
    }

    @Autowired
    private SoundUtil soundUtil;

    @Override
    public SoundDtoGet create(BaseDtoCreate<Sound, Long> entity) throws APIException {
        SoundDtoCreate obj = (SoundDtoCreate) entity;
        Long ticketId = obj.getTicketId();
        Sound s = obj.toEntry();

        Ticket t = ticketRepository.findById(ticketId).orElse(null);
        if (t == null) throw new ObjectNotFoundException("TicketId {0} not found!", ticketId.toString());

        s.setTicketNumber(t.getTicketNumber());
        s.setPatientName(t.getPatientName());
        s.setModalityId(t.getModalityID());

        Modality m = modalityRepository.findById(t.getModalityID()).orElse(null);
        if (m == null) throw new ObjectNotFoundException("ModalityId {0} not found!", t.getModalityID().toString());
        if (m.getSpeakerId() == null) throw new InvalidOperationOnObjectException("ModalityId {0} has no speaker!", t.getModalityID().toString());

        s.setSpeakerId(m.getSpeakerId());
        s.setModalityRoom(m.getRoomName());
        try {
            byte[] bytes = soundUtil.speak(t.getTicketNumber(), m.getRoomNameAudio());
            s.setAudio(bytes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        soundRepository.save(s);

        return new SoundDtoGet(s);
    }

    private void deleteSounds(PageOfData<SoundDtoGet> result) throws APIException {
        if (result == null || result.getElements() == null || result.getElements().isEmpty()) return;

        List<SoundDtoGet> sounds = result.getElements();
        List<Long> soundIds = sounds.stream().map(soundDto -> soundDto.getId()).collect(Collectors.toList());
        audioResourceService.deleteByIDs(soundIds);
    }

    @Override
    public PageOfData<SoundDtoGet> getPageOfData(BaseFilter<Sound> filter, PaginationInfo pagingInfo) throws APIException {
        PageOfData result = super.getPageOfData(filter, pagingInfo);
        deleteSounds(result);
        return result;
    }

}

package vn.com.itechcorp.module.procedure.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;
import vn.com.itechcorp.base.persistence.repository.BaseRepository;
import vn.com.itechcorp.module.procedure.model.Procedure;

import java.util.List;

@Repository("procedureRepository")
public interface ProcedureRepository extends BaseRepository<Procedure, Long> {

    @Override
    @EntityGraph(value = "procedureGraph", type = EntityGraph.EntityGraphType.FETCH)
    List<Procedure> findAllById(Iterable<Long> longs);

}

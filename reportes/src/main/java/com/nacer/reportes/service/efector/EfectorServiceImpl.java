package com.nacer.reportes.service.efector;

import com.nacer.reportes.model.Efector;
import com.nacer.reportes.model.Region;
import com.nacer.reportes.repository.efector.EfectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EfectorServiceImpl implements EfectorService{

    @Autowired
    EfectorRepository efectorRepository;

    @Override
    public Optional<Efector> getEfectorPorCuie(String cuie) {
        return efectorRepository.findByCuie(cuie);
    }

    @Override
    public List<Efector> getEfectorPorNombre(String nombre) {
        return efectorRepository.findByNombre(nombre);
    }

    @Override
    public List<Efector> getEfectorPorRegion(Region region) {
        return efectorRepository.findByRegion(region);
    }

    @Override
    public Optional<Efector> getEfectorPorId(UUID id) {
        return efectorRepository.findById(id);
    }

    @Override
    public void guardarEfector(Efector efector) {
        efectorRepository.save(efector);
    }
}

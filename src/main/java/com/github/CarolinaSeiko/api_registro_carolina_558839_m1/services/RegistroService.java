package com.github.CarolinaSeiko.api_registro_carolina_558839_m1.services;

import com.github.CarolinaSeiko.api_registro_carolina_558839_m1.dto.RegistroRequestDTO;
import com.github.CarolinaSeiko.api_registro_carolina_558839_m1.dto.RegistroResponseDTO;
import com.github.CarolinaSeiko.api_registro_carolina_558839_m1.entities.RegistroMeteorologico;
import com.github.CarolinaSeiko.api_registro_carolina_558839_m1.repositories.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;

    @Transactional(readOnly = true)
    public List<RegistroResponseDTO> findAll() {
        return registroRepository.findAll()
                .stream()
                .map(RegistroResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public RegistroResponseDTO findById(Long id) {
        RegistroMeteorologico registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro nao encontrado"));

        return new RegistroResponseDTO(registro);
    }

    @Transactional
    public RegistroResponseDTO save(RegistroRequestDTO requestDTO) {
        RegistroMeteorologico registro = new RegistroMeteorologico();
        copyDtoToEntity(requestDTO, registro);
        registro = registroRepository.save(registro);
        return new RegistroResponseDTO(registro);
    }

    @Transactional
    public RegistroResponseDTO update(Long id, RegistroRequestDTO requestDTO) {
        RegistroMeteorologico registro = registroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro nao encontrado"));

        copyDtoToEntity(requestDTO, registro);
        registro = registroRepository.save(registro);
        return new RegistroResponseDTO(registro);
    }

    @Transactional
    public void delete(Long id) {
        if (!registroRepository.existsById(id)) {
            throw new RuntimeException("Registro nao encontrado");
        }
        registroRepository.deleteById(id);
    }

    private void copyDtoToEntity(RegistroRequestDTO requestDTO,
                                 RegistroMeteorologico registro) {
        registro.setEstacaoNome(requestDTO.getEstacaoNome());
        registro.setDataRegistro(requestDTO.getDataRegistro());
        registro.setTemperatura(requestDTO.getTemperatura());
        registro.setPercentualUmidade(requestDTO.getPercentualUmidade());
        registro.setPrecipitacao(requestDTO.getPrecipitacao());
        registro.setCondicaoClimatica(requestDTO.getCondicaoClimatica());
    }
}

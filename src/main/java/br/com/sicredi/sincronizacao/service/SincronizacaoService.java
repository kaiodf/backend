package br.com.sicredi.sincronizacao.service;

import br.com.sicredi.sincronizacao.SicredException;
import br.com.sicredi.sincronizacao.dto.ContaDTO;
import br.com.sicredi.sincronizacao.dto.ContaResponseDTO;
import br.com.sicredi.sincronizacao.timer.MeasuredExecutionTime;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class SincronizacaoService {

    @Autowired
    BancoCentralService bancoCentralService;

    final static String newFilePath = "NEW_DATA".concat(LocalDateTime.now().toString()).concat(".csv");

    @MeasuredExecutionTime
    public void syncAccounts(String path) {
        try{
            List<ContaResponseDTO> contas = getContas(path);
            writeNewList(contas);
        } catch (SicredException e) {
            log.error(e.getMessage());
        }
    }

    private static void writeNewList(List<ContaResponseDTO> contas) {
        try (Writer writer = Files.newBufferedWriter(Paths.get(newFilePath))) {
            StatefulBeanToCsv<ContaResponseDTO> beanToCsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
            beanToCsv.write(contas);
            writer.flush();
            log.info("Arquivo: ".concat(newFilePath).concat(" gerado com sucesso"));
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            log.error(e.getMessage());
        }
    }

    private List<ContaResponseDTO> getContas(String path) {
        if(path==null){
            throw new SicredException("ERROR: O arquivo não pode ser nulo");
        }
        log.info("Preparando o arquivo: ".concat(path).concat(" para ser lido"));
        try(Stream<String> st = Files.lines(Paths.get(path))){
            return st.skip(1)
                    .map(line -> line.split(","))
                    .map(col -> new ContaDTO(col[0], col[1], Double.parseDouble(col[2])))
                    .map(conta -> new ContaResponseDTO(conta, bancoCentralService.atualizaConta(conta)))
                    .toList();
        }catch (IOException e){
            log.error(e.getMessage());
            return null;
        }
    }
}

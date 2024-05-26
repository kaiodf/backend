package br.com.sicredi.sincronizacao.service;

import br.com.sicredi.sincronizacao.BackendAppTestApplication;
import br.com.sicredi.sincronizacao.dto.ContaResponseDTO;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = BackendAppTestApplication.class)
class SincronizacaoServiceTest {

    private SincronizacaoService sincronizacaoService;

    @Autowired
    SincronizacaoServiceTest(SincronizacaoService sincronizacaoService) {
        this.sincronizacaoService = sincronizacaoService;
    }

    @BeforeEach
    void init() {
        List<ContaResponseDTO> contas = new ArrayList<>();
        contas.add(new ContaResponseDTO("9444", "21382-2", 880.2, true));
        contas.add(new ContaResponseDTO("0201", "29139-9", 716.19, true));
        contas.add(new ContaResponseDTO("4828", "63483-3", 416.01, true));
        try (Writer writer = Files.newBufferedWriter(Paths.get("teste.csv"))) {
            StatefulBeanToCsv<ContaResponseDTO> beanToCsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
            beanToCsv.write(contas);
            writer.flush();
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void destroyer() throws IOException {
        Files.delete(Paths.get("teste.csv"));
    }

    @Test
    void whenRequestIsValidThenGeneratesAndSendsListBacen() throws IOException {
        sincronizacaoService.syncAccounts("teste.csv");

        Assertions.assertTrue(Files.exists(Paths.get("teste.csv")));
    }

    @Test
    void whenRequestIsInvalidThenNullPointerException() throws IOException {
        sincronizacaoService.syncAccounts("novoTeste.csv");
        Assertions.assertFalse(Files.exists(Paths.get("novoTeste.csv")));
    }
}
package br.com.sicredi.sincronizacao;

import br.com.sicredi.sincronizacao.service.SincronizacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.System.exit;

@SpringBootApplication
@Slf4j
public class SincronizadorBacen implements CommandLineRunner {
	@Autowired
	SincronizacaoService sincronizacaoService;

	public static void main(String[] args) {
		SpringApplication.run(SincronizadorBacen.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(args.length>0) {
			sincronizacaoService.syncAccounts(args[1]);
		} else {
			log.info("Precisa informar o path do arquivo a ser lido");
		}
		exit(0);
	}
}

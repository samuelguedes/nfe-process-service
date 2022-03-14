package br.gov.scheduler;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.Timestamp;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.gov.dtos.NotaFiscalDTO;
import br.gov.services.NotaFiscalService;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class ProcessadorArquivoScheduler  {

	@Inject
	NotaFiscalService notaFiscalService;

	@Transactional
    @Scheduled(every = "120s", identity = "processador-arquivo-schedule") 
    void schedule() {
		File currentFile = null;
		File inputFolder = new File("/home/samuel/desenv/projetos/nfe/arquivos/input/");
		File outputFolder = new File("/home/samuel/desenv/projetos/nfe/arquivos/output/");
		File erroFolder = new File("/home/samuel/desenv/projetos/nfe/arquivos/erro/");
		try { 
			for (final File fileEntry : inputFolder.listFiles()) {
 				currentFile = new File(fileEntry.getPath());
				outputFolder = new File("/home/samuel/desenv/projetos/nfe/arquivos/output/" + currentFile.getName());
				erroFolder = new File("/home/samuel/desenv/projetos/nfe/arquivos/erro/" + currentFile.getName());

				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
				DocumentBuilder db = dbf.newDocumentBuilder();  
				Document doc = db.parse(currentFile);  
				doc.getDocumentElement().normalize();
				NodeList notaList = doc.getElementsByTagName("element");  
				for (int itr = 0; itr < notaList.getLength(); itr++) {  
					Node nota = notaList.item(itr);
					if (nota.getNodeType() == Node.ELEMENT_NODE) {  
						Element notaElemento = (Element) nota;
						NotaFiscalDTO notaFiscalDTO = new NotaFiscalDTO();
						notaFiscalDTO.setChave(notaElemento.getElementsByTagName("chave").item(0).getTextContent());
						notaFiscalDTO.setDataHoraRegistro(new Date(new Timestamp(Long.parseLong(notaElemento.getElementsByTagName("dataHoraRegistro").item(0).getTextContent())).getTime()));
						notaFiscalDTO.setDescricaoStatus(notaElemento.getElementsByTagName("descricaoStatus").item(0).getTextContent());
						notaFiscalDTO.setNomeArquivo(notaElemento.getElementsByTagName("nomeArquivo").item(0).getTextContent());
						notaFiscalDTO.setNomeDestinatario(notaElemento.getElementsByTagName("nomeDestinatario").item(0).getTextContent());
						notaFiscalDTO.setNomeEmitente(notaElemento.getElementsByTagName("nomeEmitente").item(0).getTextContent());
						notaFiscalDTO.setValor(Double.parseDouble(notaElemento.getElementsByTagName("valor").item(0).getTextContent()));
						// NodeList duplicataList = notaElemento.getElementsByTagName("duplicatas");
						// for (int dtr = 0; dtr < duplicataList.getLength(); dtr++) {  
						// 	Node duplicata = duplicataList.item(dtr);
						// 	if (nota.getNodeType() == Node.ELEMENT_NODE) {
						// 		Element duplicataElemento = (Element) duplicata;
						// 		DuplicataDTO duplicataDTO = new DuplicataDTO();
						// 		duplicataDTO.setDataVencimento(new Date(new Timestamp(Long.parseLong(duplicataElemento.getElementsByTagName("dataVencimento").item(0).getTextContent())).getTime()));
						// 		duplicataDTO.setNumeroParcela(Long.parseLong(duplicataElemento.getElementsByTagName("numeroParcela").item(0).getTextContent()));
						// 		duplicataDTO.setValorParcela(Double.parseDouble(duplicataElemento.getElementsByTagName("valorParcela").item(0).getTextContent()));
						// 		duplicataDTO.setNotaFiscalDTO(notaFiscalDTO);
						// 		notaFiscalDTO.getDuplicatas().add(duplicataDTO);
						// 	}
						// }
						notaFiscalService.save(notaFiscalDTO);
					}
				}
				Files.copy(currentFile.toPath(), outputFolder.toPath(), REPLACE_EXISTING);
				Files.delete(currentFile.toPath());
			}
		} catch (Exception e) {  
			try {
				Log.error(e.getMessage(), e);
				Files.copy(currentFile.toPath(), erroFolder.toPath(), REPLACE_EXISTING);
				Files.delete(currentFile.toPath());
			} catch (IOException ioe) {
				Log.error(ioe.getMessage(), ioe);
			}
		}
    }
}

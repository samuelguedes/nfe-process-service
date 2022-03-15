package br.gov.scheduler;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.Timestamp;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.microprofile.config.inject.ConfigProperty;
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

	@ConfigProperty(name = "nfe-process-service.diretorio.input") 
	private String pathInput;

	@ConfigProperty(name = "nfe-process-service.diretorio.output") 
	private String pathOutput;

	@ConfigProperty(name = "nfe-process-service.diretorio.erro") 
	private String pathErro;

	@Inject
	NotaFiscalService notaFiscalService;

    @Scheduled(every = "120s", identity = "processador-arquivo-schedule") 
    void schedule() {
		File inputFolder = new File(pathInput);
		File erroFolder = null;
		File currentFile = null;
		File outputFolder = null;
		try { 
			for (final File fileEntry : inputFolder.listFiles()) {
				currentFile = new File(fileEntry.getPath());
				erroFolder = new File(pathErro + currentFile.getName());
				outputFolder = new File(pathOutput + currentFile.getName());

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				
				Document documento = builder.parse(currentFile);
				documento.getDocumentElement().normalize();
				NodeList notaList = documento.getElementsByTagName("notaFiscal");			
				for (int temp = 0; temp < notaList.getLength(); temp++) {
					NotaFiscalDTO notaFiscalDTO = new NotaFiscalDTO();
					Node node = notaList.item(temp);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element notaElemento = (Element) node;
						notaFiscalDTO.setChave(notaElemento.getElementsByTagName("chave").item(0).getTextContent());
						notaFiscalDTO.setDataHoraRegistro(new Date(new Timestamp(Long.parseLong(notaElemento.getElementsByTagName("dataHoraRegistro").item(0).getTextContent())).getTime()));
						notaFiscalDTO.setDescricaoStatus(notaElemento.getElementsByTagName("descricaoStatus").item(0).getTextContent());
						notaFiscalDTO.setNomeArquivo(notaElemento.getElementsByTagName("nomeArquivo").item(0).getTextContent());
						notaFiscalDTO.setNomeDestinatario(notaElemento.getElementsByTagName("nomeDestinatario").item(0).getTextContent());
						notaFiscalDTO.setNomeEmitente(notaElemento.getElementsByTagName("nomeEmitente").item(0).getTextContent());
						notaFiscalDTO.setValor(Double.parseDouble(notaElemento.getElementsByTagName("valor").item(0).getTextContent()));
						// NodeList duplicataList = notaElemento.getElementsByTagName("duplicatas");
						// for (int duplicataTemp = 0; duplicataTemp < duplicataList.getLength(); duplicataTemp++) {
						// 	Node nodeDuplicata = notaList.item(duplicataTemp);
						// 	if (nodeDuplicata.getNodeType() == Node.ELEMENT_NODE) {
						// 		Element duplicataElemento = (Element) nodeDuplicata;
						// 		DuplicataDTO duplicataDTO = new DuplicataDTO();
						// 		duplicataDTO.setDataVencimento(new Date(new Timestamp(Long.parseLong(duplicataElemento.getElementsByTagName("dataVencimento").item(0).getTextContent())).getTime()));
						// 		duplicataDTO.setNumeroParcela(Long.parseLong(duplicataElemento.getElementsByTagName("numeroParcela").item(0).getTextContent()));
						// 		duplicataDTO.setValorParcela(Double.parseDouble(duplicataElemento.getElementsByTagName("valorParcela").item(0).getTextContent()));
						// 		duplicataDTO.setNotaFiscalDTO(notaFiscalDTO);
						// 		notaFiscalDTO.getDuplicatas().add(duplicataDTO);
						// 	}
						// }
					}
					notaFiscalService.inserir(notaFiscalDTO);
				}
			}
			if(inputFolder.listFiles().length != 0){
				moverArquivo(currentFile, outputFolder);
			}
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
			moverArquivo(currentFile, erroFolder);
		}	
    }

	private void moverArquivo(File origem, File destino) {
		try {
			Files.move(origem.toPath(), destino.toPath(), REPLACE_EXISTING);
		} catch (IOException ioe) {
			Log.error(ioe.getMessage(), ioe);
		}
	}
}

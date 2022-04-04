package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report" })
public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The dao. */
	DAO dao = new DAO();
	
	/** The contato. */
	JavaBeans contato = new JavaBeans();

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
		super();
	}

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {
			removerContato(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		} else	{
			response.sendRedirect("index.html");
		}
	}

	/**
	 * Contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Criando um objeto que ir� receber os dados JavaBeans
		ArrayList<JavaBeans> lista = dao.listarContatos();
		// teste de recebimento da lista
		// for (int i = 0; i < lista.size(); i++) {
		// System.out.println(lista.get(i).getIdcon());
		// System.out.println(lista.get(i).getNome());
		// System.out.println(lista.get(i).getFone());
		// System.out.println(lista.get(i).getEmail());
		// }
		// Encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}

	/**
	 * Novo contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Novo contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// teste de recebimento dos dados do formul�rio
		// System.out.println(request.getParameter("nome"));
		// System.out.println(request.getParameter("fone"));
		// System.out.println(request.getParameter("email"));
		// setar as vari�veis JavaBeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// invocar o m�todo inserirContato passando o objeto contato
		dao.inserirContato(contato);
		// redirecionar para o documento agenda.jsp
		response.sendRedirect("main");
	}

	/**
	 * Listar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Editar Contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recebimento do id do contato que ser� editado
		String idcon = request.getParameter("idcon");
		// System.out.println(idcon);
		// Setar a vari�vel JavaBeans
		contato.setIdcon(idcon);
		// Executar o m�todo selecionarContato (DAO)
		dao.selecionarContato(contato);
		// teste de recebimento
		// System.out.println(contato.getIdcon());
		// System.out.println(contato.getNome());
		// System.out.println(contato.getFone());
		// System.out.println(contato.getEmail());
		// Setar os atributos do formul�rio com o conte�do JavaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		// Encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}

	/**
	 * Editar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// teste de recebimento
		// System.out.println(request.getParameter("Idcon"));
		// System.out.println(request.getParameter("nome"));
		// System.out.println(request.getParameter("fone"));
		// System.out.println(request.getParameter("email"));
		// setar as vari�veis JavaBeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// executar o m�todo alterarContato
		dao.alterarContato(contato);
		// redirecionar para o documento agenda.jsp (atualizando as altera��es)
		response.sendRedirect("main");
	}

	/**
	 * Remover contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Remover um contato
	protected void removerContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recebimento do id do contato a ser exclu�do
		String idcon = request.getParameter("idcon");
		// System.out.println(idcon);
		contato.setIdcon(idcon);
		// executar o m�todo deletarContato (DAO) passando o objeto contato
		dao.deletarContato(contato);
		// redirecionar para o documento agenda.jsp (atualizando as altera��es)
		response.sendRedirect("main");
	}

	/**
	 * Gerar relatorio.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Gerar relat�rio em PDF
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document documento = new Document();
		try {
			//tipo de conte�do
			response.setContentType("apllication/pdf");
			//nome do documento
			response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");
			// criar o documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			//abrir o documento -> conte�do
			documento.open();
			documento.add(new Paragraph("Lista de contatos:"));
			documento.add(new Paragraph(" "));
			// criar tabela
			PdfPTable tabela = new PdfPTable(3);
			// cabe�alho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			// popular a tabela com os contatos
			ArrayList<JavaBeans> lista = dao.listarContatos();
			for (int i = 0; i < lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());
			}
			documento.add(tabela);
			documento.close();
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
	}
	
}


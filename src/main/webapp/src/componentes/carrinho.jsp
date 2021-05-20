<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@page import="model.Livro"%>
<%@page import="model.LivroDAO"%>
<div id="toggler" onclick="toogleNav.call(this)"></div>

<jsp:useBean class="model.LivroDAO" id="livroDA" />
<div id="carrinho" class="d-flex flex-column align-items-start">

	<c:choose>
		<c:when test="${sessionScope.carrinho == null }">
			<div class="d-flex flex-column justify-content-center align-items-center h-100 p-3">
				<p>Opa! Parece que o seu carrinho está vazio :(</p>
				<svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" fill="currentColor" class="bi bi-cart-x" viewBox="0 0 16 16">
	  			<path d="M7.354 5.646a.5.5 0 1 0-.708.708L7.793 7.5 6.646 8.646a.5.5 0 1 0 .708.708L8.5 8.207l1.146 1.147a.5.5 0 0 0 .708-.708L9.207 7.5l1.147-1.146a.5.5 0 0 0-.708-.708L8.5 6.793 7.354 5.646z"/>
	  			<path d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1H.5zm3.915 10L3.102 4h10.796l-1.313 7h-8.17zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0z"/>
				</svg>
			</div>
		</c:when>
		
		<c:otherwise>
			<div id="resumo-carrinho" class="resumo-carrinho">
				<c:forEach var="livroId" items="${sessionScope.carrinho}">
			 		<c:set var="livro" value="${livroDA.getById(livroId)}" /> 		
			    <div class="item-carrinho">
				    <img class="thumb-livro-carrinho me-3"
				        src="${pageContext.request.contextPath}/imagens/fotosLivro/${livro.capa}"
				        alt="capa do livro">  
				        
				    <p>${livro.nome}</p>
			    </div>
				</c:forEach>
				<a onclick="agendar()" class="btn btn-cadastro mt-3 w-50 mx-auto my-auto">
					Alugar
				</a>
			</div>
				<div id="agendamento" style="display:none">
					<form action="${pageContext.request.contextPath}/gerenciar_aluguel.do" method="POST">
											
						<select name="dataHorario">
							<option>01/05</option>
							<option>02/05</option>
							<option>03/05</option>
							<option>04/05</option>
						</select>
						<select name="dataHorario">
							<option>07:30</option>
							<option>07:45</option>
							<option>08:00</option>
							<option>08:15</option>
						</select>
						
						<button class="btn btn-cadastro" type="submit">Confirmar</button>
					</form>
				</div>
		</c:otherwise>
	</c:choose>
		
</div>

<script>

function agendar() {
	let resumoCarrinho = document.getElementById('resumo-carrinho');
	resumoCarrinho.style.display = 'none';
	
	let agendamento = document.getElementById('agendamento');
	agendamento.style.display = 'flex';
}

let sidenav = document.getElementById('carrinho');
let toggler = document.getElementById('toggler');
let open = false;

function toogleNav() {
  if (!open) { // se tiver fechado, vai abrir
	  sidenav.style.left = "0";
  } else { // se tiver aberto, vai fechar
    sidenav.style.left = "-18rem";
  }
  open = !open;
}
</script>
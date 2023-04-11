const formulario = document.querySelector("form");
const Ipergunta = document.querySelector(".pergunta");
const Iresposta = document.querySelector(".resposta");

function cadastrar(){
    fetch("http://localhost:8080/chat", {
        headers: {
            'Accept':'application/json',
            'Content-Type': 'application/json',
        },
        method: 'POST',
        body: JSON.stringify({
            pergunta: Ipergunta.value,
        }),
    })
    .then(function (res) {
        return res.text();
    })
    .then(function (data) {
        console.log(data)
        Iresposta.value = data;
    })
    .catch(function (err) {
        console.log(err);
    });
}

function limpar(){
    Ipergunta.value = "";
}




formulario.addEventListener('submit', function (event) {
    event.preventDefault();
    cadastrar();
    limpar();
});
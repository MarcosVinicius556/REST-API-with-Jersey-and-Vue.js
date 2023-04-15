var app = new Vue({
    el: "#app",
    data:{
        funcionario: {
            nome: '',
            idade: '',
            salario: '',
            email: '',
            setor: ''
        }
    },
    methods:{
        enviaCadastro: function(cadastro){
            alert('Enviando cadastro');
        }
    }
});
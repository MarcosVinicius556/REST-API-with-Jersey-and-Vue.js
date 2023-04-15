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
    created(){

    },
    methods:{
        enviaCadastro: function(cadastro){
            alert('Enviando cadastro');
        }
    }
});
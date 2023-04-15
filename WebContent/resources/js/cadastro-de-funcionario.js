var app = new Vue({
    el: "#app",
    data:{
        funcionario: {
            nome: '',
            idade: '',
            salario: '',
            email: '',
            setor: {}
        },
        setores: []

    },
    created: function(){
        let vm = this;
        vm.buscaSetores();
    },
    methods:{
        buscaSetores: function(){
            const vm = this;
            axios.get("/funcionarios/rest/setores/listar")
                 .then(response => {
                    vm.setores = response.data;
                 })
                 .catch(error => {
                    vm.mostraAlertaErro("Erro interno", error);  
                 });
        },
        enviaCadastro: function(){
            const vm = this;
            axios.post("/funcionarios/rest/funcionarios/salvar")
                 .body(vm.funcionario);    
        },
		mostraAlertaErro: function(error, mensagem){
			console.log(error);
			alert(mensagem);
		}
    }
});
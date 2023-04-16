const RETORNO_COM_SUCESSO = 200;

var app = new Vue({
    el: "#app",
    data:{
        currentRoute: window.location.pathname,
        funcionario: {
            nome: '',
            idade: '',
            salario: '',
            email: '',
            setor: {
                id: '',
                nome: ''
            }
        },
        listaSetor: [],

    },
    created: function(){
        let vm = this;
        vm.buscaSetores();
    },
    methods:{
        buscaSetores: async function(){
            const vm = this;
            axios.get("/funcionarios/rest/setores/listar")
                 .then(response => {
                    vm.listaSetor = response.data;
                 })
                 .catch(error => {
                    vm.mostraAlertaErro("Erro interno", error);  
                 });
        },
        cadastrar: async function(e){
            e.preventDefault();
            const vm = this;
            console.log(vm.funcionario);

            axios.post("/funcionarios/rest/funcionarios/salvar", {
             nome: vm.funcionario.nome,
             email: vm.funcionario.email,
             idade: vm.funcionario.idade,
             salario: vm.funcionario.salario,
             setor: {
                id: vm.funcionario.setor.id,
                nome: vm.funcionario.setor.nome
                }
            }).then(response => {
                let status = response.status;
                if(status == RETORNO_COM_SUCESSO){
                    alert("Funcionário cadastrado com sucesso!");
                } else {
                    alert("Não foi possível cadastrar o funcionário!");
                }
            });
        },
		mostraAlertaErro: function(error, mensagem){
			console.log(error);
			alert(mensagem);
		}
    }
});
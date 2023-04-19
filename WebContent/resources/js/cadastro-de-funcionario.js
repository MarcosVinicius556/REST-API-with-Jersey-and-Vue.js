const RETORNO_COM_SUCESSO = 200;

var app = new Vue({
    el: "#cadastro",
    data:{
        showModal: false,
		modalTitle: '',
		msgModal: '',
        conteudoCarregado: false,
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
        vm.conteudoCarregado = true;
    },
    methods:{
        buscaSetores: async function(){
            const vm = this;
            axios.get("/funcionarios/rest/setores/listar")
                 .then(response => {
                    vm.listaSetor = response.data;
                 }).catch(error => {
                    vm.openModal("Erro interno", error);  
                 });
        },
        cadastrarFuncionario: async function(e){
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
                    vm.openModal("Sucesso", "Funcionário cadastrado com sucesso!");
                } else {
                    vm.openModal("Erro", "Não foi possível cadastrar o funcionário!");
                }
            });
            // window.location.href = "../index.html";
        },
        /**
		 * Controles do MODAL
		 */
		openModal: function(titulo, msg){
			const vm = this;
			vm.modalTitle = titulo;
			vm.msgModal = msg;
			vm.showModal = true;
		}, 
		closeModal: function(){
			const vm = this;
			vm.showModal = false;
			vm.modalTitle = '';
			vm.msgModal = '';
		}
    }
});
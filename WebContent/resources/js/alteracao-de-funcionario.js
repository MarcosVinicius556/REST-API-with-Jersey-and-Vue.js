const RETORNO_COM_SUCESSO = 200;

var app = new Vue({
    el: "#alteracao",
    data:{
        showModal: false,
		modalTitle: '',
		msgModal: '',
        conteudoCarregado: false,
        funcionario: {
            id: '',
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
        /**
         * Carregar os dados do funcionário aqui
         */
        let vm = this;
        const urlSearchParams = new URLSearchParams(window.location.search);
        vm.funcionario.id = urlSearchParams.get("id");
        vm.buscaSetores();
        vm.buscaFuncionario();
    },
    methods:{
        buscaSetores: async function(){
            const vm = this;
            axios.get("/funcionarios/rest/setores/listar")
                 .then(response => {
                    vm.listaSetor = response.data;
                 })
                 .catch(error => {
                    vm.openModal("Erro interno", "Não foi possível carregar os setores");  
                    console.log(error);
                 });
            vm.conteudoCarregado = true;
        },
        buscaFuncionario: function(){
            const vm = this;
            axios.get(`/funcionarios/rest/funcionarios/buscar/${vm.funcionario.id}`)
                 .then(response => {
                    vm.funcionario = response.data;
                 })
                 .catch(error => {
                    vm.openModal(error, "Erro ao buscar funcionário!");
                 });
        },
        alterarFuncionario: async function(e){
            e.preventDefault();
            const vm = this;
            console.log(vm.funcionario);

            axios.put("/funcionarios/rest/funcionarios/atualizar", {
             id: vm.funcionario.id,
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
                    vm.openModal("Sucesso", "Funcionário alterado!");
                } else {
                    vm.openModal("Erro", "Não foi possível alterar o funcionário!");
                }
            }).catch(error => {
                vm.openModal("Erro interno", "Não foi possível atualizar o funcionário");
                console.log(error);
            });
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

            window.location.href = '../index.html';
		}
    }
});
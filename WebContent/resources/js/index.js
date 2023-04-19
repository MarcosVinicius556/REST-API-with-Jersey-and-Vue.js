const VIEW_CADASTRO_FUNCIONARIO = 'cadastrar';
const VIEW_EDITAR_FUNCIONARIO = 'editar';

const RETORNO_COM_SUCESSO = 200;

var inicio = new Vue({
	el:"#inicio",
    data: {
        lista: [],
		showModal: false,
		modalTitle: '',
		msgModal: ''
    },
    created: function(){
        let vm =  this;
        vm.listarFuncionarios();
    },
    methods:{
		/*
		 * Busca os itens para a lista da primeira página
		 */
        listarFuncionarios: function(){
			const vm = this;
			axios.get("/funcionarios/rest/funcionarios/listar")
				 .then(response => {
						vm.lista = response.data;
				 }).catch(function (error) {
						vm.openModal('Erro Interno', 'Não foi possível listar os itens. Motivo: ' + error);
				 })
		},
		/**
		 * Exclusão do funcionário
		 */
		redirectToDelete: function(funcionario){
			const vm = this;
			axios.delete('/funcionarios/rest/funcionarios/deletar/'+funcionario.id)
				 .then(response => {
						let status = response.status;
						if(status == RETORNO_COM_SUCESSO){
							vm.openModal('Atenção', 'Funcionário removido com sucesso!');
						} else {
							vm.openModal('Erro Interno', 'Não foi possível remover o funcionário selecionado!');
						}
					})
			window.location.href = "./index.html";
		},
		/**
		 * insert de um novo funcionário
		 */
		redirectToCreate: function(){
			const vm = this;
			window.location.href = "./pages/novo-funcionario.html";
		},
		/**
		 * Update do funcionário
		 */
		redirectToUpdate: function(funcionario){
			const vm = this;
			window.location.href = `./pages/alterar-funcionario.html?id=${funcionario.id}`;
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
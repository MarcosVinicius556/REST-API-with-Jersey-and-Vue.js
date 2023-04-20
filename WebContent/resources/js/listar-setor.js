const RETORNO_COM_SUCESSO = 200;

var inicio = new Vue({
	el:"#setores",
    data: {
        lista: [],
		showModal: false,
		modalTitle: '',
		msgModal: '',
		conteudoCarregado: false
    },
    created: function(){
        let vm =  this;
        vm.listarSetores();
    },
    methods:{
		/*
		 * Busca os itens para a lista da primeira página
		 */
        listarSetores: function(){
			const vm = this;
			axios.get("/funcionarios/rest/setores/listar")
				 .then(response => {
						vm.lista = response.data;
				 }).catch(function (error) {
						vm.openModal('Erro Interno', 'Não foi possível listar os itens. Motivo: ' + error);
				 })
			vm.conteudoCarregado = true;
		},
		/**
		 * Exclusão do funcionário
		 */
		redirectToDelete: function(setor){
			const vm = this;
			axios.delete('/funcionarios/rest/setores/deletar/'+setor.id)
				 .then(response => {
						let status = response.status;
						if(status == RETORNO_COM_SUCESSO){
							vm.openModal('Atenção', 'setor removido com sucesso!');
						} else {
							vm.openModal('Erro Interno', 'Não foi possível remover o setor selecionado!');
						}
					})
			window.location.href = "listar-setor.html";
		},
		/**
		 * insert de um novo funcionário
		 */
		redirectToCreate: function(){
			const vm = this;
			window.location.href = "novo-setor.html";
		},
		/**
		 * Update do funcionário
		 */
		redirectToUpdate: function(setor){
			const vm = this;
			window.location.href = `alterar-setor.html?id=${setor.id}`;
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
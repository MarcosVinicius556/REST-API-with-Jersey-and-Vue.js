const RETORNO_COM_SUCESSO = 200;

var app = new Vue({
    el: "#alteracao-setor",
    data:{
        showModal: false,
		modalTitle: '',
		msgModal: '',
        conteudoCarregado: false,
        setor: {
            id: '',
            nome: '',
        },
    },
    created: function(){
        /**
         * Carregar os dados do setor aqui
         */
        let vm = this;
        const urlSearchParams = new URLSearchParams(window.location.search);
        vm.setor.id = urlSearchParams.get("id");
        vm.buscaSetor();
    },
    methods:{
        buscaSetor: function(){
            const vm = this;
            axios.get(`/funcionarios/rest/setores/buscar/${vm.setor.id}`)
                 .then(response => {
                    vm.setor = response.data;
                 })
                 .catch(error => {
                    vm.openModal(error, "Erro ao buscar setor!");
                 });
            vm.conteudoCarregado = true;
        },
        alterarSetor: async function(e){
            e.preventDefault();
            const vm = this;
            console.log(vm.setor);

            axios.put("/funcionarios/rest/setores/atualizar", {
                id: vm.setor.id,
                nome: vm.setor.nome
            }).then(response => {
                let status = response.status;
                if(status == RETORNO_COM_SUCESSO){
                    vm.openModal("Sucesso", "Setor alterado!");
                } else {
                    vm.openModal("Erro", "Não foi possível alterar o setor!");
                }
            }).catch(error => {
                vm.openModal("Erro interno!", "Não foi possível alterar o funcionário");
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

            window.location.href = '../pages/listar-setor.html';
		}
    }
});
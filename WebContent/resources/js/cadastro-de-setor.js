const RETORNO_COM_SUCESSO = 200;

var app = new Vue({
    el: "#cadastro-setor",
    data:{
        showModal: false,
		modalTitle: '',
		msgModal: '',
        conteudoCarregado: false,
        setor: {
            nome: ''
        }
    },
    created: function(){
        let vm = this;
        vm.conteudoCarregado = true;
    },
    methods:{
        cadastrarSetor: async function(e){
            e.preventDefault();
            const vm = this;
            console.log(vm.setor);

            axios.post("/funcionarios/rest/setores/salvar", {
                nome: vm.setor.nome
            }).then(response => {
                let status = response.status;
                if(status == RETORNO_COM_SUCESSO){
                    vm.openModal("Sucesso", "Setor cadastrado com sucesso!");
                } else {
                    vm.openModal("Erro", "Não foi possível cadastrar o setor!");
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
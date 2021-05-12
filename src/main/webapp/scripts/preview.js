let previewContainer = document.getElementById('preview-container');
function mostrarPreview () {
  let preview = document.getElementById('preview');

  previewContainer && previewContainer.addEventListener('click', () => {
    removerImagem(preview)
    preview.click();
  })
}

function lerURL (input) {
  if (input.files && input.files[0]) {
    let reader = new FileReader();

    reader.onload = (e) => {
      /* Caso não exclua o preview antes (se existir), serão adicionadas
      várias imagens se o usuário clicar para adicionar novamente */
      let previewExiste = document.getElementById('previewImg');
      previewExiste && previewExiste.remove();

      let img = document.createElement('img');
      img.id = 'previewImg';
      img.className = 'img-fluid';
      img.setAttribute("src", e.target.result);
      img.alt = "preview da foto de perfil";

      let cameraIcone = document.getElementById('camera-icone');
      cameraIcone && cameraIcone.remove(); // se eu deixar o ícone, ele vai atrapalhar a preview

      previewContainer.append(img);
    }
    reader.readAsDataURL(input.files[0]);
  }
}

function removerImagem (preview) {
  preview.value = null;
}
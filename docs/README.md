# Documentação

Essa é uma prévia da documentação, e não pode ser considerada uma versão final. É possível que a documentação
esteja desatualizada em relação ao app, pois ainda é a versão alpha.

## Requisitos

### Para desenvolver para Android

Para compilar o aplicativo, é necessário o Android SDK instalado.

### Para simular

Um navegador instalado no PC. Habilite seguindo o tutorial do EasyApp Simulator, em Features.

### Para desenvolver para Web

Um navegador instalado. Habilite seguindo o tutorial do EasyApp Web, em Features.

## Tutoriais básicos

### Configurações

Modifique a classe "Settings", presente no pacote "org.hiperesp.easyapp.core.settings", ou o arquivo
"Settings.java" no caminho "app/src/main/java/org/hiperesp/easyapp/core/settings/".

### Desenvolvimento

A pasta de desenvolvimento é "app/src/main/assets/www/". Você pode colocar quaisquer arquivos fora da pasta
"www".

#### Atenção

Não remova a pasta "easyapp_core" (dentro de "app/src/main/assets/www/") caso queira utilizar o simulador, a
versão Web ou as chamadas nativas. Ela contém os scripts do simulador, os scripts de encapsulamento das
chamadas nativas e os scripts da versão Web.

## Features

### EasyApp Simulator (opcional)

O simulador simula uma interação mais próxima do app Android pelo navegador, e ele inclui tela de confirmação
de permissões e algumas telas que podem manipular os dados fornecidos pela chamada nativa.

Você pode incluir o script "easyapp_core/EasyAppNativeInterfaceSimulator.js" para utilizar o simulador.

Para iniciar o simulador, você pode apenas abrir o arquivo index.html utilizando um servidor local, ou não.

#### Atenção

O simulador não pode ser considerado uma versão web. Ao contrário da versão web, essa versão simula o ambiente
Android, e inclui algumas telas que podem modificar o resultado final, como tela de permissão e etc. Quaisquer
chamadas nativas possuem retorno simulado.

Não utilize simultaneamente com a versão Web. Há conflitos.

### EasyApp Web (opcional, em breve)

A versão web possibilita você utilizar o mesmo código do app Android em um site, fornecendo mais uma opção de
acesso aos seus usuários.

#### Atenção

A versão web não é o simulador. Ao contrário dele, essa versão realmente funciona. Quaisquer chamadas nativas
serão encapsuladas para uma versão do navegador. Nem todas podem estar disponíveis.

Algumas funções podem requisitar obrigatoriamente um servidor web com ssl (https).

Não utilize simultaneamente com o simulador. Há conflitos.

## Chamadas Nativas (opcional, mas recomendado)

As chamadas nativas são recursos nativos do celular, que geralmente não estão disponíveis através do
navegador.

Para incluir chamadas nativas utilizando um código mais limpo, você pode incluir o script
"easyapp_core/EasyAppNativeInterfaceCaller.js". Todos os exemplos vão utilizar esse script.

### Câmera

Exemplo de câmera.

Esse exemplo é caso o app precise de uma foto do usuário.

```
EasyAppNativeInterface.requestCameraPhoto()
.then((data) => {
    let img = document.createElement("img")
	img.src = "data:image/png;base64,"+data
	document.querySelector("body").appendChild(img)
})
.catch((error) => {
	if(error==-1)
		alert("Você negou a permissão")
	else if(error==-2)
		alert("Você cancelou a foto")
});
```

#### Atenção:

Está sendo implantado na versão Web.
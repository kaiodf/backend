## Solução

Você deve implementar uma solução que leia um arquivo Excel e realize a atualização das contas junto ao Banco Central.

Em suma, a solução deverá ser uma aplicação Spring _standalone_ que receba como argumento o diretório do arquivo CSV contendo as contas.

`Exemplo: java -jar sincronizacao-0.0.1-SNAPSHOT.jar SincronizadorBacen /home/DATA.csv`

A solução deve processar cada conta do arquivo, enviando-a ao Banco Central por meio da classe BancoCentralService.

Ao fim do processamento deve-se retornar um arquivo contendo o resultado do envio da atualização. Mesclando o conteúdo original e uma nova coluna chamada `status`.

## Pre requisito
java 21

gradle

## Execução
- baixa do repositorio (https://github.com/kaiodf/backend.git)

- acesse o diretorio do código fonte pela linha de comando

`Exemplo /home/user/workspace/backend`

- execute o comando

gradle build

- execute o jar gerado na basta build/libs com o segundo parametro sendo o csv de teste aqui

`Exemplo java -jar sincronizacao-0.0.1-SNAPSHOT.jar SincronizadorBacen /home/DATA.csv`
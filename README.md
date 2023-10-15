# fiap-blog-nosql
FIAP Pos tech - Projeto de um blog usando NoSQL

# Comandos para fazer Backup e Restore.

### Backup
*Sendo out, a pasta onde sera armazenada o bkp*
> mongodump --out "c:/mongo/bkp"


### Restore
*Sendo nsInclude, o nome do database a ser restaurado, host, porta e local onde esta o bkp do database*
> mongorestore --nsInclude=blog.* --host=localhost --port=27017 c:/mongo/bkp


Lembrando que na pasta onde esta do executavel do mongo db, deve conter os seguintes arquivos:
![image](https://github.com/Daniel-Nasciment/fiap-blog-nosql/assets/65513073/cae77620-e7be-4629-a0ca-3d076bbbe54a)

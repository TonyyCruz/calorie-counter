Projeto pessoal de um contador de calorias
Tabela: https://app.diagrams.net/?src=about#G18qz6MEHwDqLJGmj3Vrm8KOLPPxX7-zC_#%7B%22pageId%22%3A%22JfLuquS_KaX4VP3Qba6E%22%7D

[] alterar o Id do daily consume utilizado ao adinionar novas refeicoes por data

[] Criar classe para testar operaços de não logados de todas as rotas

[] Criar metodo para verificar autorizacao 

[] Criar docker compose 

[] adicionar front-end simples com Thymeleaf

[] Criar docker compose


controle de autorização utolizando SpEl

```@Service
public class PlaylistService {

  // Só ADMIN pode acessar
  @PreAuthorize("hasRole('ADMIN')")
  public void deletePlaylist(Long id) {
    System.out.println("Playlist deletada!");
  }

  // Só usuários maiores de 18 anos podem criar playlist
  @PreAuthorize("@ageSecurity.checkAge(authentication, 18)")
  public void createPlaylist(String name) {
    System.out.println("Playlist criada!");
  }

  // Só o dono (mesmo id) ou ADMIN pode acessar
  @PreAuthorize("#userId == authentication.principal.id or hasRole('ADMIN')")
  public void getUserData(Long userId) {
    System.out.println("Dados do usuário retornados");
  }
}
```

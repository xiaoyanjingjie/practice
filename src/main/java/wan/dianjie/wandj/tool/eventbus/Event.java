package wan.dianjie.wandj.tool.eventbus;

public interface Event<T> {
  T getContent();
}

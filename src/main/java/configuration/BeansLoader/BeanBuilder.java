package configuration.BeansLoader;

import java.util.ArrayList;
import java.util.List;

public class BeanBuilder <T> {
    private List<T> list = new ArrayList<>();
    private BeanFactory<T> beanFactory;
    private int amount;

    public BeanBuilder (int amount) {
        this.amount = amount;
    }

    public List<T> load() {
        for (int i = 0; i < amount; i++) {
            list.add(createBean());
        }
        return this.list;
    }

    public T createBean() {
        return beanFactory.createBean();
    }



    public void setBeanFactory(BeanFactory<T> beanFactory) {
        this.beanFactory = beanFactory;
    }


}

package zyc.squaresql;

import dagger.Component;

@Component(
    modules = {
        MainActivityModule.class,
    }) public interface MainActivityComponent {
  void inject(MainActivity mainActivity);
}
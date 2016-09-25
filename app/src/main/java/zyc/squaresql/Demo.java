package zyc.squaresql;

import com.google.auto.value.AutoValue;
import com.squareup.sqldelight.RowMapper;

@AutoValue public abstract class Demo implements DemoModel {
  public static final Factory<Demo> FACTORY = new Factory<>(AutoValue_Demo::new);

  public static final RowMapper<Demo> MAPPER = FACTORY.select_allMapper();
  public static final RowMapper<Demo> MAPPER_NAME = FACTORY.select_by_nameMapper();
  public static final RowMapper<Demo> MAPPER_VALUE = FACTORY.select_by_valueMapper();
}

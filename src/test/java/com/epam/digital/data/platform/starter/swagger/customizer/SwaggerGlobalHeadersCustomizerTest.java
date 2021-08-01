package com.epam.digital.data.platform.starter.swagger.customizer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import com.epam.digital.data.platform.starter.swagger.config.OpenApiRequestParamProperties;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SwaggerGlobalHeadersCustomizerTest {

  private static final String CUSTOM_HEADER = "header";

  private SwaggerGlobalHeadersCustomizer customizer;

  @Mock
  private OpenApiRequestParamProperties openApiRequestParamProperties;

  @Test
  void expectCustomParametersAddedFromPropertiesHeaders() {
    when(openApiRequestParamProperties.getHeaders())
        .thenReturn(Collections.singletonList(CUSTOM_HEADER));
    customizer = new SwaggerGlobalHeadersCustomizer(openApiRequestParamProperties);

    Operation result = customizer.customize(new Operation(), null);

    assertThat(result.getParameters().size()).isEqualTo(1);
    Parameter actualParam = result.getParameters().get(0);
    assertThat(actualParam.getIn()).isEqualTo(ParameterIn.HEADER.toString());
    assertThat(actualParam.getName()).isEqualTo(CUSTOM_HEADER);
    assertThat(actualParam.getSchema()).isInstanceOf(StringSchema.class);
    assertThat(actualParam.getRequired()).isFalse();
  }
}

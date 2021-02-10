/*
 * Copyright 2021 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License
 * for the specific language governing permissions and limitations under
 * the License.
 */

package com.netflix.spinnaker.clouddriver.ecs.cache;

import static com.netflix.spinnaker.clouddriver.ecs.cache.Keys.Namespace.APPLICATIONS;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.spinnaker.cats.cache.DefaultCacheData;
import com.netflix.spinnaker.clouddriver.ecs.cache.client.ApplicationCacheClient;
import com.netflix.spinnaker.clouddriver.ecs.cache.model.Application;
import com.netflix.spinnaker.clouddriver.ecs.provider.agent.ApplicationCachingAgent;
import java.util.Collections;
import java.util.Map;
import org.junit.Test;
import spock.lang.Subject;

public class ApplicationCacheClientTest extends CommonCacheClient {
  ObjectMapper mapper = new ObjectMapper();

  @Subject
  private final ApplicationCacheClient client = new ApplicationCacheClient(cacheView, mapper);

  @Test
  public void shouldConvert() {
    // Given
    ObjectMapper mapper = new ObjectMapper();
    String key = Keys.getApplicationKey(ACCOUNT, APP_NAME);

    Application application = new Application();

    Map<String, Object> attributes =
        ApplicationCachingAgent.convertApplicationToAttributes(application);
    when(cacheView.get(APPLICATIONS.toString(), key))
        .thenReturn(new DefaultCacheData(key, attributes, Collections.emptyMap()));

    // When
    Application retrievedApplication = client.get(key);

    // Then
    assertTrue(
        "Expected the application to be " + application + " but got " + retrievedApplication,
        application.equals(retrievedApplication));
  }
}

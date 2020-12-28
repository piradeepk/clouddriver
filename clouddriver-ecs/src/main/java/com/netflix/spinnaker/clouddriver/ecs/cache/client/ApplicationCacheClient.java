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

package com.netflix.spinnaker.clouddriver.ecs.cache.client;

import static com.netflix.spinnaker.clouddriver.ecs.cache.Keys.Namespace.APPLICATIONS;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.spinnaker.cats.cache.Cache;
import com.netflix.spinnaker.cats.cache.CacheData;
import com.netflix.spinnaker.clouddriver.ecs.cache.model.Application;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCacheClient extends AbstractCacheClient<Application> {
  private ObjectMapper objectMapper;

  @Autowired
  public ApplicationCacheClient(Cache cacheView, ObjectMapper objectMapper) {
    super(cacheView, APPLICATIONS.toString());
    this.objectMapper = objectMapper;
  }

  @Override
  protected Application convert(CacheData cacheData) {
    Application application = new Application();
    Map<String, Object> attributes = cacheData.getAttributes();

    application.setName((String) attributes.get("name"));
    return application;
  }
}

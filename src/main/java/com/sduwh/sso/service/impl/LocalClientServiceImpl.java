package com.sduwh.sso.service.impl;

import com.sduwh.sso.mapper.ClientMapper;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

import javax.annotation.Resource;

/**
 * @author wxp
 */
public class LocalClientServiceImpl implements ClientDetailsService {
  @Resource private ClientMapper clientMapper;

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    return clientMapper.selectOneClientById(clientId);
  }
}

CREATE TABLE oauth_client_details (
  client_id   varchar2(48) NOT NULL ,
  resource_ids varchar2(256) DEFAULT NULL,
  client_secret varchar2(256) DEFAULT NULL,
  scope clob DEFAULT NULL,
  authorized_grant_types varchar2(256) DEFAULT NULL,
  web_server_redirect_uri varchar2(256) DEFAULT NULL,
  authorities varchar2(256) DEFAULT NULL,
  access_token_validity number(11) DEFAULT NULL,
  refresh_token_validity number(11) DEFAULT NULL,
  additional_information clob DEFAULT NULL,
  autoapprove varchar2(256) DEFAULT NULL,
  PRIMARY KEY (client_id)
)

CREATE TABLE oauth_user_authority (
  authority_id NUMBER(20, 0) NOT NULL,
  user_id   varchar2(32) NOT NULL ,
  authority_type varchar2(32) NOT NULL,
  authority clob NOT NULL,
  PRIMARY KEY (authority_id)
);
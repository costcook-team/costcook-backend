package com.costcook.domain.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminLoginRequest {
  private String username;
  private String password;
}

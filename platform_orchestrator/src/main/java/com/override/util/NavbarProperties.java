package com.override.util;

import lombok.Data;
import lombok.Getter;

import java.util.List;


@Data
@Getter
public class NavbarProperties {

    private List<String> admin;

    private List<String> user;
}

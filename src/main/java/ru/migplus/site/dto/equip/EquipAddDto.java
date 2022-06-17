package ru.migplus.site.dto.equip;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class EquipAddDto {
    private String name;
    private String number;
    private String year;
    private long consumerId;
    private ArrayList<String> equipOpers;
}


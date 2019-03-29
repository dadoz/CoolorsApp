package com.application.dev.david.coolorsapp.modules.colorGrid;

import java.util.List;

public interface ColorGridView {
    void onColorGrid(List<String> list);
    void onColorGridError(String error);
}

package core.ui.verifiers;

import core.logic.battleships.Battleship;
import core.module.BattleShipGameType;
import core.module.ShipTypeType;

import java.util.List;
import java.util.Map;

/**
 * Created by barakm on 15/08/2017
 */
public interface IInputVerifier {

    boolean isFileOk(String filePath, ErrorCollector errorCollector);

    boolean isContentOK(BattleShipGameType inputContent, ErrorCollector errorCollector);

    boolean isBattleshipAmountOk(List<Battleship> battleships, Map<String, ShipTypeType> battleshipsMap);
}

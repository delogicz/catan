package jsonstructures;

import java.util.List;

import model.Harbour;
import model.Island;
import model.TerrainType;
import model.Tile;
import model.TokenNumber;

/**
 * This class is for the map of the island to initialize it
 * @author Felixi, Georg
 *
 */
public class KartenDaten {
	private FeldDaten[] Felder;
	private GebaeudeDaten[] Gebaeude;
	private HafenDaten[] Haefen;
	private OrtDaten Raeuber;
	
	
	/**
	 * Creates a KartenDaten object and initializes the map
	 * @param felder		array of tiles
	 * @param gebaeude		array of buildings
	 * @param haefen		array of harbors
	 * @param raeuber		position of the robber
	 */
	public KartenDaten(FeldDaten[] felder, GebaeudeDaten[] gebaeude, HafenDaten[] haefen, String raeuber) {
		Felder = felder;
		Gebaeude = gebaeude;
		Haefen = haefen;
		Raeuber = new OrtDaten(raeuber);
	}
	
	/**
	 * Constructor for Server
	 * @param island
	 * @param server
	 */
	public KartenDaten(Island island, boolean server) {
		Raeuber = new OrtDaten(ConvertMaster.convertTile(island.getRobber().getTile(), server));
		
		Harbour[] harbours = island.getHarbours();
		Haefen = new HafenDaten[harbours.length];
		int[] harbourPositions = island.getHarbourPositions();
		for(int j = 0; j < harbours.length; j++){
			
			Haefen[j] = new HafenDaten(ConvertMaster.convertIntToEdge(harbourPositions[j]), ConvertMaster.convertHarbour(harbours[j]));
			
		}
		
		Gebaeude = new GebaeudeDaten[0];
		List<Tile> felder= island.getHexes();
		Felder = new FeldDaten[felder.size()+18]; //For 18 sea tiles around
		for(int j = 0; j < felder.size();j++){
			String ort = ConvertMaster.convertTile(felder.get(j), server);
			String typ = ConvertMaster.convertTerrain(felder.get(j).getTerrainType());
			int zahl = felder.get(j).getToken().getNumber();
			Felder[j] = new FeldDaten(ort,typ,zahl);
		}
		String abc = "abcdefghijklmnopqr";
		for(int k = felder.size();k < felder.size()+18;k++){
			Felder[k] = new FeldDaten(ConvertMaster.getNewTileName(abc.charAt(k-(felder.size()))+""),"Meer",0);
		}
	}

	/**
	 * Getter for the Tiles
	 * @return
	 */
	public TerrainType[] getFeldTypen() {
		TerrainType[] feldTyp = new TerrainType[Felder.length-18];
		for(FeldDaten feld : Felder){
			if(!feld.istMeer()){
				feldTyp[feld.getOrtNum()] = feld.getTyp();
			}
		}
		return feldTyp;
	}
	
	/**
	 * Getter for The token numbers
	 * @return
	 */
	public TokenNumber[] getFeldZahlen(){
		TokenNumber[] feldZahl = new TokenNumber[Felder.length-18];
		for(FeldDaten feld : Felder){
			if(!feld.istMeer()){
				feldZahl[feld.getOrtNum()] = feld.getZahl();
			}
		}
		return feldZahl;
	}

	/**
	 * Setter for the tiles
	 * @param felder
	 */
	public void setFelder(FeldDaten[] felder) {
		Felder = felder;
	}

	/**
	 * Getter for the GebaeudeDaten
	 * @return
	 */
	public GebaeudeDaten[] getGebaeude() {
		return Gebaeude;
	}

	/**
	 * Setter for the GebaeudeDaten
	 * @param gebaeude
	 */
	public void setGebaeude(GebaeudeDaten[] gebaeude) {
		Gebaeude = gebaeude;
	}

	/**
	 * Setter for the HafenDaten
	 * @param haefen
	 */
	public void setHaefen(HafenDaten[] haefen) {
		Haefen = haefen;
	}

	/**
	 * Getter for the Raeuber
	 * @return
	 */
	public OrtDaten getRaeuber() {
		return Raeuber;
	}

	/**
	 * Setter for the Raeuber
	 * @param raeuber
	 */
	public void setRaeuber(String raeuber) {
		Raeuber = new OrtDaten(raeuber);
	}
	
	/**
	 * Gets the harbour array. 
	 * @return the harbour array in the right order
	 */
	public Harbour[] getHaefen() {
		Harbour[] harbours = new Harbour[Haefen.length];
		for(int j = 0; j < Haefen.length; j++){
			HafenDaten next = Haefen[j];
			harbours[j] = ConvertMaster.convertToHarbour(next.getTyp());
		}
		return harbours;
	}
	
	/**
	 * Gets the edges array of positions of the harbours 
	 * @return the int array in the right order
	 */
	public int[] getHafenPositionen() {
		int[] harbourPositions = new int[Haefen.length];
		for(int j = 0; j < Haefen.length; j++){
			HafenDaten next = Haefen[j];
			harbourPositions[j] = ConvertMaster.convertToEdgeInt(next.getOrt()[0],next.getOrt()[1]);
		}
		return harbourPositions;
	}
	
}

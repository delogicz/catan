package jsonstructures;

import controller.LogMaster;
import game.Settings;
import model.DevelopmentCardType;
import model.Edge;
import model.Harbour;
import model.Island;
import model.ResourceType;
import model.TerrainType;
import model.Tile;
import model.Vertex;

/**
 * This static class converts can convert all edges, vertices, tiles from and to the server protocol
 * @author Felixi, Georg
 *
 */
public class ConvertMaster {
	
	private static Island serverIsland;
	private static Island clientIsland;
	
	/**
	 * Contains all Edges send by the server
	 */
	private static String[] EDGE_NAMES = {"aA","eA","bA","bB","AB","cB","cC","BC","dC","fC","eL",
			"gl","AL","AM","LM","BM","BN","MN","CN","CD","ND","fD","hD","gK","iK","LK","LR","KR",
			"MR","MS","RS","NS","NO","SO","DO","DE","OE","hE","jE","kK","KJ","kJ","RJ","RQ","JQ",
			"SQ","SP","QP","OP","OF","PF","EF","lE","lF","mJ","JI","mI","QI","QH","IH","PH","PG",
			"HG","FG","nF","nG","oI","pI","pH","qH","qG","rG"};
	
	/**
	 * Contains all Vertices send by the server
	 */
	private static String[] VERTEX_NAMES = {"aeA","abA","bAB","bcB","cBC","cdC","dCf","egL","eAL",
			"ALM","ABM","BMN","BCN","CND","CfD","fDh","giK","gLK","LKR","LMR","MRS","MNS","NSO",
			"NDO","DOE","DhE","hEj","jKk","KkJ","KRJ","RJQ","RSQ","SQP","SOP","OPF","OEF","EFl",
			"Ejl","kJm","Jml","JQI","QIH","QPH","PHG","PFG","FGn","Fln","mIo","Iop","IHp","Hpq",
			"HGq","Gqr","Gnr"};

	/**
	 * Contains all Tiles send by the server
	 */
	private static String[] TILE_NAMES = {"A","B","C","L","M","N","D","K","R","S","O","E","J","Q",
			"P","F","I","H","G"};
	
	/**
	 * Converts the names for tiles, edges and vertices to protocol 1.0 standards
	 */
	private static void convertNames(){
		for(int i = 0; i < TILE_NAMES.length; i++){
			TILE_NAMES[i] = getNewTileName(TILE_NAMES[i]);
		}
		for(int i = 0; i < EDGE_NAMES.length; i++){
			String en1 = EDGE_NAMES[i].substring(0,1);
			String en2 = EDGE_NAMES[i].substring(1,2);
			en1 = getNewTileName(en1);
			en2 = getNewTileName(en2);
			EDGE_NAMES[i] = en1+","+en2;
		}
		for(int i = 0; i < VERTEX_NAMES.length; i++){
			String vn1 = VERTEX_NAMES[i].substring(0,1);
			String vn2 = VERTEX_NAMES[i].substring(1,2);
			String vn3 = VERTEX_NAMES[i].substring(2,3);
			vn1 = getNewTileName(vn1);
			vn2 = getNewTileName(vn2);
			vn3 = getNewTileName(vn3);
			VERTEX_NAMES[i] = vn1+","+vn2+","+vn3;
		}
	}
	
	/**
	 * Returns the protocol 1.0 name of a tile from the protocol 0.x name
	 * @param oldName the protocol 0.x name of a tile
	 * @return the protocol 1.0 name of the tile
	 */
	public static String getNewTileName(String oldName){
		switch(oldName){
		case "a": return("{\"x\":0,\"y\":3}");
		case "b": return("{\"x\":1,\"y\":3}");
		case "c": return("{\"x\":2,\"y\":3}");
		case "d": return("{\"x\":3,\"y\":3}");
		case "e": return("{\"x\":-1,\"y\":2}");
		case "f": return("{\"x\":3,\"y\":2}");
		case "g": return("{\"x\":-2,\"y\":1}");
		case "h": return("{\"x\":3,\"y\":1}");
		case "i": return("{\"x\":-3,\"y\":0}");
		case "j": return("{\"x\":3,\"y\":0}");
		case "k": return("{\"x\":-3,\"y\":-1}");
		case "l": return("{\"x\":2,\"y\":-1}");
		case "m": return("{\"x\":-3,\"y\":-2}");
		case "n": return("{\"x\":1,\"y\":-2}");
		case "o": return("{\"x\":-3,\"y\":-3}");
		case "p": return("{\"x\":-2,\"y\":-3}");
		case "q": return("{\"x\":-1,\"y\":-3}");
		case "r": return("{\"x\":0,\"y\":-3}");
		case "A": return("{\"x\":0,\"y\":2}");
		case "B": return("{\"x\":1,\"y\":2}");
		case "C": return("{\"x\":2,\"y\":2}");
		case "D": return("{\"x\":2,\"y\":1}");
		case "E": return("{\"x\":2,\"y\":0}");
		case "F": return("{\"x\":1,\"y\":-1}");
		case "G": return("{\"x\":0,\"y\":-2}");
		case "H": return("{\"x\":-1,\"y\":-2}");
		case "I": return("{\"x\":-2,\"y\":-2}");
		case "J": return("{\"x\":-2,\"y\":-1}");
		case "K": return("{\"x\":-2,\"y\":0}");
		case "L": return("{\"x\":-1,\"y\":1}");
		case "M": return("{\"x\":0,\"y\":1}");
		case "N": return("{\"x\":1,\"y\":1}");
		case "O": return("{\"x\":1,\"y\":0}");
		case "P": return("{\"x\":0,\"y\":-1}");
		case "Q": return("{\"x\":-1,\"y\":-1}");
		case "R": return("{\"x\":-1,\"y\":0}");
		case "S": return("{\"x\":0,\"y\":0}");
		default: return null;
		}
	}
	
	/**
	 * Setter for the serverIsland
	 * @param newIsland
	 */
	public static void setServerIsland(Island newIsland){
		if(TILE_NAMES[0].length()<2){
			convertNames();
		}
		serverIsland = newIsland;
	}
	
	/**
	 * Setter for the clientIsland
	 * @param newIsland
	 */
	public static void setClientIsland(Island newIsland){
		if(TILE_NAMES[0].length()<2){
			convertNames();
		}
		clientIsland = newIsland;
	}

	/**
	 * Converts 2 Ortdaten to the Edge
	 * @param ort1 
	 * @param ort2 
	 * @param server
	 * @return		Edge
	 */
	public static Edge convertToEdge(OrtDaten ort1, OrtDaten ort2, boolean server){
		String ort = "{\"x\":" + ort1.getX() + ",\"y\":" + ort1.getY() + "},{\"x\":" + ort2.getX() + ",\"y\":" + ort2.getY() + "}";
		for(int i = 0;i < EDGE_NAMES.length; i++){
			if(EDGE_NAMES[i].equals(ort)){
				if(server) return serverIsland.getEdges().get(i);
				return clientIsland.getEdges().get(i);
			}
		}
		return null;
	}
	
	/**
	 * Converts String Edge to int-position in the edges
	 * @param ort
	 * @return int
	 */
	public static int convertToEdgeInt(String ort) {
		for(int i = 0;i < EDGE_NAMES.length; i++){
			if(EDGE_NAMES[i].equals(ort)){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * Converts OrtDaten Edge to int-position in the edges
	 * @param ort1
	 * @param ort2 
	 * @return int
	 */
	public static int convertToEdgeInt(OrtDaten ort1, OrtDaten ort2) {
		String ort = "{\"x\":" + ort1.getX() + ",\"y\":" + ort1.getY() + "},{\"x\":" + ort2.getX() + ",\"y\":" + ort2.getY() + "}";
		for(int i = 0;i < EDGE_NAMES.length; i++){
			if(EDGE_NAMES[i].equals(ort)){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * Converts an integer value to our model of Edges
	 * @param i
	 * @return
	 */
	public static String convertIntToEdge(int i) {
		return EDGE_NAMES[i];
	}
	
	/**
	 * Converts String Vertex to the Vertex
	 * @param vertex
	 * @param server
	 * @return
	 */
	
	public static Vertex convertToVertex(String vertex, boolean server){
		for(int i = 0;i < VERTEX_NAMES.length; i++){
			if(VERTEX_NAMES[i].equals(vertex)){
				if(server) return serverIsland.getVertex(i);
				return clientIsland.getVertex(i);
			}
		}
		return null;
	}
	
	/**
	 * Converts the String hafen to a Harbour
	 * @param hafen the String to be converted
	 * @return the harbour
	 */
	public static Harbour convertToHarbour(String hafen) {
		switch(hafen){
		case "Hafen" 	     	: return Harbour.THREETOONE;
		case "Lehm Hafen"   	: return Harbour.TWOTOONEBRICK;
		case "Getreide Hafen" 	: return Harbour.TWOTOONEGRAIN;
		case "Wolle Hafen" 		: return Harbour.TWOTOONEWOOL;
		case "Holz Hafen" 		: return Harbour.TWOTOONELUMBER;
		case "Erz Hafen"		: return Harbour.TWOTOONEORE;
		}
		return null;
	}
	
	/**
	 * Converts String Tile to the Tile
	 * @param tile
	 * @param server
	 * @return
	 */
	public static Tile convertToTile(String tile, boolean server){
		for(int i = 0;i < TILE_NAMES.length; i++){
			if(TILE_NAMES[i].equals(tile)){
				if(server) return serverIsland.getHexagon(i);
				return clientIsland.getHexagon(i);
			}
		}
		return null;
	}
	
	/**
	 * Converts OrtDaten Tile to the Tile
	 * @param tile
	 * @param server
	 * @return
	 */
	public static Tile convertToTile(OrtDaten tile, boolean server){
		String ort = "{\"x\":" + tile.getX() + ",\"y\":" + tile.getY() + "}";
		for(int i = 0;i < TILE_NAMES.length; i++){
			if(TILE_NAMES[i].equals(ort)){
				if(server) return serverIsland.getHexagon(i);
				return clientIsland.getHexagon(i);
			}
		}
		return null;
	}
	
	/**
	 * Converts Edge to the String edge
	 * @param edge
	 * @param server
	 * @return
	 */
	
	public static String convertEdge(Edge edge, boolean server){
		if(TILE_NAMES[0].length()<2){
			convertNames();
		}
		Island island;
		if(server) island = serverIsland;
		else island = clientIsland;
		for(int i = 0; i < island.getEdges().size();i++){
			if(edge.equals(island.getEdges().get(i))){
				return "["+EDGE_NAMES[i]+"]"; 
			}
		}
		return null;
	}
	
	/**
	 * Converts the Vertex to the String vertex
	 * @param vertex
	 * @param server
	 * @return
	 */
	
	public static String convertVertex(Vertex vertex, boolean server){
		if(TILE_NAMES[0].length()<2){
			convertNames();
		}
		Island island;
		if(server) island = serverIsland;
		else island = clientIsland;
		for(int i = 0; i<island.getVertices().size();i++){
			if(vertex.equals(island.getVertices().get(i))){
				return VERTEX_NAMES[i];
			}
		}
		LogMaster.log("[Exc]ConvertMaster unable to convert vertex to string:  " + vertex.getPos()+":"+vertex.getRow() + " [Thread : "+ Thread.currentThread().getId() + "]");
		return null;
	}
	
	/**
	 * Converts the Tile to the String tile
	 * @param tile
	 * @param server
	 * @return
	 */
	
	public static String convertTile(Tile tile, boolean server){
		if(TILE_NAMES[0].length()<2){
			convertNames();
		}
		Island island;
		if(server) island = serverIsland;
		else island = clientIsland;
		for(int i = 0; i<island.getHexes().size();i++){
			if(tile.equals(island.getHexes().get(i))){
				return TILE_NAMES[i];
			}
		}
		return null;
	}
	
	/**
	 * Converts String of tile to the number of the tile
	 * @param name
	 * @return
	 */
	public static int getTileNumber(String name){
		for(int i=0; i < TILE_NAMES.length; i++){
			if(name.equals(TILE_NAMES[i])){
				return i;
			}
		}
		return -1;
	}

	/**
	 * Converts OrtDaten of tile to the number of the tile
	 * @param tile
	 * @return
	 */
	public static int getTileNumber(OrtDaten tile){
		if(TILE_NAMES[0].length()<2){
			convertNames();
		}
		String ort = "{\"x\":" + tile.getX() + ",\"y\":" + tile.getY() + "}";
		for(int i = 0;i < TILE_NAMES.length; i++){
			if(TILE_NAMES[i].equals(ort)){
				return i;
			}
		}
		LogMaster.logExc("[EXC]Tile not found, maybe ConvertMaster wasn't initialized properly:" + ort);
		return -1;
	}
	
	/**
	 * Vonverts TerrainType to a String
	 * @param terrainType TerrainType to be converted
	 * @return TerrainType as a String
	 */
	public static String convertTerrain(TerrainType terrainType){
			switch(terrainType){
			case FOREST: return "Wald";
			case HILLS: return "Huegelland";
			case PASTURE: return "Weideland";
			case FIELDS: return "Ackerland";
			case MOUNTAINS: return "Gebirge";
			case DESERT: return "Wueste";
			}
			LogMaster.logExc("[EXC]ConversionMaster unable to convert TerrainType to String\"" + terrainType.toString() + "\"");

			return "ERROR";
	}
	
	/**
	 * Converts the Harbour to a String hafen
	 * @param harbour
	 * @return the String 
	 */
	public static String convertHarbour(Harbour harbour) {
		switch(harbour){
		case THREETOONE 	: return "Hafen";
		case TWOTOONEBRICK 	: return "Lehm Hafen";
		case TWOTOONEGRAIN 	: return "Getreide Hafen";
		case TWOTOONEWOOL 	: return "Wolle Hafen";
		case TWOTOONELUMBER : return "Holz Hafen";
		case TWOTOONEORE	: return "Erz Hafen";
		}
		LogMaster.logExc("[EXC]ConversionMaster unable to convert Harbour to String\"" + harbour.toString() + "\"");
		return "ERROR";
	}

	/**
	 * Converts a ResourceType to a String
	 * @param resourceType
	 * @return the String
	 */
	public static String convertResource(ResourceType resourceType){
		switch(resourceType){
		case LUMBER: return "Holz";
		case BRICK: return "Lehm";
		case WOOL: return "Wolle";
		case GRAIN: return "Getreide";
		case ORE: return "Erz";
		}
		LogMaster.logExc("[EXC]ConversionMaster unable to convert ResourceType to String:\"" + resourceType.toString() + "\"");

		return "ERROR";
	}
	
	/**
	 * Converts a DevelopmentCardType to a String
	 * @param devCardType the DevelopmentCardType
	 * @return the String
	 */
	public static String convertDevCard(DevelopmentCardType devCardType){
		switch(devCardType){
		case KNIGHT: return "Ritter"; 
		case VICTORYPOINT: return "Siegpunkt";
		case MONOPOLY: return "Monopol";
		case ROADBUILDING: return "Strassenbaukarte";
		case YEAROFPLENTY:	return "Erfindung";	
		}
		LogMaster.logExc("[EXC]ConversionMaster unable to convert DevCardType to String:\"" + devCardType.toString() + "\"");

		return "ERROR";
	}
	
	/**
	 * Converts a String to a DevelopmentCardType
	 * @param devCardType the String
	 * @return the DevelopmentCardType
	 */
	public static DevelopmentCardType convertDevCard(String devCardType){
		switch(devCardType){
		case "Ritter": return DevelopmentCardType.KNIGHT; 
		case "Siegpunkt": return DevelopmentCardType.VICTORYPOINT;
		case "Monopol": return DevelopmentCardType.MONOPOLY;
		case "Strassenbaukarte": return DevelopmentCardType.ROADBUILDING;
		case "Erfindung": return DevelopmentCardType.YEAROFPLENTY;	
		}
		LogMaster.logExc("[EXC]ConversionMaster unable to convert String to DevCardType:\"" + devCardType + "\"");
		return null;
	}
	
	/**
	 * Converts a String to ResourceType
	 * @param resourceType the String
	 * @return the ResourceType
	 */
	public static ResourceType convertToResource(String resourceType){
		switch(resourceType){
		case Settings.HOLZ: return ResourceType.LUMBER;
		case Settings.LEHM: return ResourceType.BRICK;
		case Settings.WOLLE: return ResourceType.WOOL;
		case Settings.GETREIDE: return ResourceType.GRAIN;
		case Settings.ERZ: return ResourceType.ORE;
		}
		LogMaster.logExc("[EXC]ConversionMaster unable to convert String to Resource:\"" + resourceType + "\"");
		return null;
	}
	
	/**
	 * Converts name of an Edge into an OrtDaten array with the coordinates
	 * @param st name of an Edge
	 * @return OrtDaten array with the coordinates
	 */
	public static OrtDaten[] convertStringToEdgeLocation(String st){
		String s = "";
		if(st.startsWith("[")){
			s = st.substring(1, st.length()-1);
		}else{
			s = st;
		}
		String[] namen = s.split("\\},\\{");
		OrtDaten[] Ort = new OrtDaten[2];
		Ort[0] = new OrtDaten(namen[0]+"}");
		Ort[1] = new OrtDaten("{"+namen[1]);
		return Ort;
	}
	
	/**
	 * Converts name of an Vertex into an OrtDaten array with the coordinates
	 * @param st name of an Vertex
	 * @return OrtDaten array with the coordinates
	 */
	public static OrtDaten[] convertStringToVertexLocation(String st){
		String s = "";
		if(st.startsWith("[")){
			s = st.substring(1, st.length()-1);
		}else{
			s = st;
		}
		String[] namen = s.split("\\},\\{");
		OrtDaten[] Ort = new OrtDaten[3];
		Ort[0] = new OrtDaten(namen[0]+"}");
		Ort[1] = new OrtDaten("{"+namen[1]+"}");
		Ort[2] = new OrtDaten("{"+namen[2]);
		return Ort;
	}
	
	/**
	 * Converts 3 OrtDaten coordinates to a vertex
	 * @param ort1
	 * @param ort2
	 * @param ort3
	 * @param server
	 * @return the vertex
	 */
	public static Vertex convertToVertex(OrtDaten ort1, OrtDaten ort2, OrtDaten ort3, boolean server) {
		String name = "{\"x\":" + ort1.getX() + ",\"y\":" + ort1.getY() 
		+ "},{\"x\":" +ort2.getX() + ",\"y\":" + ort2.getY() 
		+ "},{\"x\":" + ort3.getX() + ",\"y\":" + ort3.getY() + "}";
		for(int i = 0;i < VERTEX_NAMES.length; i++){
			if(VERTEX_NAMES[i].equals(name)){
				if(server) return serverIsland.getVertex(i);
				return clientIsland.getVertex(i);
			}
		}
		LogMaster.logExc("[EXC]ConversionMaster unable to convert OrtDaten to Edge:\"" + name + "\"");
		return null;
	}


	/**
	 * Converts a String to an edge
	 * @param ort
	 * @param server
	 * @return the edge
	 */
	public static Edge convertToEdge(String ort, boolean server) {
		String s = "";
		if(ort.startsWith("[")){
			s = ort.substring(1, ort.length()-1);
		}else{
			s = ort;
		}
		for(int i = 0;i < EDGE_NAMES.length; i++){
			if(EDGE_NAMES[i].equals(s)){
				if(server) return serverIsland.getEdges().get(i);
				return clientIsland.getEdges().get(i);
			}
		}
		LogMaster.logExc("[EXC]ConversionMaster unable to convert String to Edge:\"" + s + "\"");
		return null;
	}
	
}
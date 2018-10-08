package model;

/**
 * Determines all different types of tiles (terrains). Contains associated ResourceType.
 *
 */
public enum TerrainType {
	HILLS (ResourceType.BRICK),
	FIELDS (ResourceType.GRAIN),
	PASTURE (ResourceType.WOOL),
	FOREST (ResourceType.LUMBER),
	MOUNTAINS (ResourceType.ORE),
	DESERT (null);
	
	private final ResourceType produce;
	
	/**
	 * Produce
	 * @param produce
	 */
	private TerrainType(ResourceType produce){
		this.produce = produce;
	}
	/**
	 * Getter for Produce
	 * @return
	 */
	public ResourceType getProduce(){
		return produce;
	}
}

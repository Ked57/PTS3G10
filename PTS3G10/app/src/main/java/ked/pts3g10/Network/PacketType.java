package ked.pts3g10.Network;

public enum PacketType {

	AUTH(0, 3),
	ERRORAUTH(1, 1),
	SUCCESSAUTH(2, 2), 
	WAITINGGAME(3, 2);
	
	private int id;
	private int paramLength;
	
	private PacketType(int id, int paramLength) {
		this.id = id;
		this.paramLength = paramLength;
	}
	
	public static PacketType getById(Integer var) {
		for(PacketType a : values()) if(var == a.getId()) return a;
		throw new NullPointerException();
	}

	public Integer getId() {
		return id;
	}
	
	public Integer getParamLength() {
		return paramLength;
	}
}

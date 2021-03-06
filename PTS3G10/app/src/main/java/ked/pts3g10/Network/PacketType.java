package ked.pts3g10.Network;

public enum PacketType {

	AUTH(0, 3),
	ERRORAUTH(1, 2),
	SUCCESSAUTH(2, 2),
	WAITINGGAME(3, 2),
	PREPAREGAME(4, 4),
	ENDGAME(5, 3),
	ENDROUND(6, 2),
	NEXTROUND(7, 2),
    IMSTILLHERE(8,2),
	RECEIVEENDGAME(9, 2),
	RECEIVEMOVEMENT(10, 5),
	RECEIVEUPDATEHP(11, 4),
    RECEIVETIMEOUT(12,2),
	SENDMOVEMENT(13, 6),
	UPDATEHP(14, 5),
	PLAYCARD(15,5),
	RECEIVEPLAYCARD(16,4),
	SENDSPELL(17,5),
	RECEIVESPELL(18,4),
    SENDLOGOUT(19,2),
    SENDREGISTER(20,4),
    RECEIVESUCCESSREGISTER(21,2),
    RECEIVEERRORREGISTER(22,2),
    SENDLEAVEWAITINGLIST(23,2);
	
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

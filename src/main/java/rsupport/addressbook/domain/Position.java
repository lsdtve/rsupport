package rsupport.addressbook.domain;

import lombok.Getter;

@Getter
public enum Position {
	LEADER("팀장"),
	MEMBER("팀원");

	private final String title;

	Position(String title) {
		this.title = title;
	}

	public static Position getLeaderOrElseMember(String str) {
		return LEADER.getTitle().equals(str) ? LEADER : MEMBER;
	}
}

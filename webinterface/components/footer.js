import { get_color } from "../js/style";

export default function Footer(props) {
	return (
		<footer className={"bar card left-align " + get_color().base_color}>
			<p style={{
				display: "inline-flexbox",
			}}>Copyright (c) 2022 Glowman554</p>
		</footer>
	)
}

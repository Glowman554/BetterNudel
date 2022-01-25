import { get_color } from "../js/style";

export default function Card(props) {

	return (
		<div style={{
			margin: "1rem",
			padding: "1rem",
			borderLeftWidth: "2px",
			borderLeftStyle: "solid",
		}} className={"card " + get_color().color}>
			{props.children}
		</div>
	)
}
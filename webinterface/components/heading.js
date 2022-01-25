import { get_color } from "../js/style";

export default function Heading(props) {
	return (
		<header className={"container center " + get_color().base_color} style={{
			padding: "50px 16px",
		}}>
			<h1 className="margin xxxlarge">
				{props.title}
			</h1>
			<p className="xlarge">
				{props.subtitle}
			</p>
		</header>
	)
}

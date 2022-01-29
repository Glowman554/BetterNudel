import React from "react";
import { get_color } from "../js/style";

export default class Button extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		return (
			<button className={"button " + get_color().base_color + " hover-" + get_color().color} onClick={
				event => {
					event.preventDefault();
					this.props.onClick(event);
				}
			}>
				{this.props.children}
			</button>
		)
	}
}
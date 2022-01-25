import React from "react";
import { api_request } from "../js/api";
import { get_color } from "../js/color";
import Card from "./card";

export default class CommandsList extends React.Component {
	constructor(props) {
		super(props);
		this.commands = {};


		this.state = ({ 
			num_commands: 0,
			rendered_commands: []
		});

		api_request("/api/v2/commands").then(data => {
			data = JSON.parse(data);
			this.commands = data;

			this.render_commands();
		});
	}

	render_commands() {
		var rendered_commands = [];

		for (var command of Object.keys(this.commands)) {
			rendered_commands.push(
				<Card>
					<h3>{ command }</h3>
					<table className={"table table-all " + get_color().color + " border-" + get_color().color}>
						<tr className={get_color().base_color + " border-" + get_color().color}>
							<td>
								<p>Short help</p>
							</td>
							<td>
								<p>{ this.commands[command].help_short }</p>
							</td>
						</tr>
						<tr className={get_color().base_color + " border-" + get_color().color}>
							<td>
								<p>Long help</p>
							</td>
							<td>
								<p>{ this.commands[command].help_long }</p>
							</td>
						</tr>
					</table>
				</Card>
			)
		}

		this.setState({
			rendered_commands: rendered_commands,
			num_commands: Object.keys(this.commands).length
		});
	}

	render() {
		return (
			<div>
				{ this.props.small ?
					<div>
						<p>
							I'm currently programmed to handle {this.state.num_commands} commands.
						</p>
					</div>
					:
					<div>
						<h1>Commands</h1>
						{ this.state.rendered_commands}
					</div> }
			</div>
		)
	}
}
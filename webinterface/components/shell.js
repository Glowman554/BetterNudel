import React from "react";
import { api_request, get_api_path, has_valid_token } from "../js/api";
import ShellEntry from "./shell_entry";

export default class Shell extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			shell_entries: [],
			prompt: `[user@${window.location.hostname}] $ `,
			input_enabled: true,
		}

		this.state.shell_entries.push(<ShellEntry entry="BetterNudel Copyright (C) 2022 Glowman554<br>This program comes with ABSOLUTELY NO WARRANTY" />);
		this.state.shell_entries.push(<ShellEntry entry="<br>" />);
		this.setState(this.state);

		// window.addEventListener("load", () => {
		// 	document.querySelector("body").style.backgroundColor = "black";
		// })

		has_valid_token().then(data => {
			if (!data) {
				this.state.shell_entries.push(<ShellEntry entry="You are not logged in. Please login to continue." />);
				this.state.input_enabled = false;
				this.setState(this.state);
			}
		})
	}

	componentDidMount() {
		document.querySelector("body").style.backgroundColor = "black";
	}

	render() {
		return (
			<div>
				<div style={{
					padding: "60px 16px",
					color: "green",
					backgroundColor: "black",
					fontFamily: "Arial, Helvetica, sans-serif",
					fontSize: "medium",
				}}>
					{this.state.shell_entries.map((entry, index) => {
						return entry;
					})}
					<div style={{
						width: "100%",
					}}>
						<p style={{
							display: "inline",
							padding: "0px",
							margin: "0px"
						}}>{this.state.prompt}</p>
						<input style={{
							display: "inline",
							width: "calc(100% - " + this.state.prompt.length + "ch)",
							border: "none",
							backgroundColor: "transparent",
							outline: "none",
							color: "green",
							fontFamily: "Arial, Helvetica, sans-serif",
							fontSize: "medium",
						}} type="text" autoComplete="off" autoCapitalize="off" readOnly={!this.state.input_enabled}  onKeyDown={
							event => {
								if (event.key === "Enter") {
									event.preventDefault();

									let value = event.target.value.trim();
									event.target.value = "";

									this.state.shell_entries.push(<ShellEntry entry={this.state.prompt + value} />);
									this.setState(this.state);

									let current_length = this.state.shell_entries.length;

									api_request("/api/v2/web?message=" + encodeURIComponent(value)).then(data => {
										data = JSON.parse(data);

										console.log(data);

										for (let entry of data) {
											switch (entry.type) {
												case "text_message":
												case "text_message_quote":
													{
														this.state.shell_entries.push(<ShellEntry entry={entry.message} />);
													}
													break;
												
												case "picture_send":
													{
														this.state.shell_entries.push(<ShellEntry entry={`<img src="${get_api_path() + "/files/" + entry.message}"></img>`} />);
													}
													break;
												
												case "audio_send":
													{
														this.state.shell_entries.push(<ShellEntry entry={`<audio src="${get_api_path() + "/files/" + entry.message}" controls></audio>`} />);
													}
													break;

												case "video_send":
													{
														this.state.shell_entries.push(<ShellEntry entry={`<video src="${get_api_path() + "/files/" + entry.message}" controls></video>`} />);
													}
													break;

												case "file_send":
													{
														this.state.shell_entries.push(<ShellEntry entry={`<a href="${get_api_path() + "/files/" + entry.message}">${entry.message}</a>`} />);
													}
													break;
												
												case "message_delete":
													{
														this.state.shell_entries[current_length - 1] = null;
													}
													break;
												
												default:
													throw new Error("Unknown type: " + entry.type);
											}

											this.setState(this.state);

										}

										event.target.scrollIntoView();
									});
								}
							}
						}></input>
					</div>
				</div>
			</div>
		)
	}
}
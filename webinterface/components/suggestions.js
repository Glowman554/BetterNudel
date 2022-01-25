import React from "react";
import { api_request } from "../js/api";

export default class Suggestions extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		return (
			<div>
				<h1>Suggestions</h1>
				<p>Sending a suggestion will help me to improve this bot.</p>
				<p style={{
					display: "inline-block",
					paddingRight: "10px"
				}}>Send a suggestion: </p>
				<input onKeyDown={
					event => {
						if (event.key === "Enter") {
							event.preventDefault();
							api_request("/api/v2/suggestion?suggestion=" + encodeURIComponent(event.target.value)).then(data => {
								alert("Server responded: " + data);
							});
						}
					}
				}></input>
			</div>
		)
	}
}
import React from "react"

const SSR = typeof window === 'undefined'

export default function NoSSR(props) {
	return (
		<React.Fragment>
			{ !SSR ? props.children : null }
		</React.Fragment>
	)
}
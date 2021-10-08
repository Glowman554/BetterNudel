package io.github.glowman554.nudel.discord.commands;

import java.io.IOException;

import io.github.glowman554.nudel.utils.ArrayUtils;
import io.github.glowman554.nudel.utils.FileUtils;
import net.shadew.json.Json;
import net.shadew.json.JsonNode;
import net.shadew.json.JsonSyntaxException;

public class PermissionManager
{
	public String file;
	public String file_content;

	public PermissionManager(String file)
	{
		this.file = file;
		this.load();
	}

	public boolean hasPermission(String user, String permission)
	{
		try
		{
			JsonNode root = Json.json().parse(file_content);

			if (root.has(user))
			{
				JsonNode user_node = root.get(user);
				String[] permissions = user_node.asStringArray();

				return ArrayUtils.contains(permissions, permission);
			}
			else
			{
				JsonNode user_node = JsonNode.stringArray(new String[] {});
				root.set(user, user_node);

				this.file_content = Json.json().serialize(root);
				this.save();
				return false;
			}
		}
		catch (JsonSyntaxException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public String[] getPermissions(String user)
	{
		try
		{
			JsonNode root = Json.json().parse(file_content);

			if (root.has(user))
			{
				JsonNode user_node = root.get(user);
				String[] permissions = user_node.asStringArray();

				return permissions;
			}
			else
			{
				JsonNode user_node = JsonNode.stringArray(new String[] {});
				root.set(user, user_node);

				this.file_content = Json.json().serialize(root);
				this.save();
				return new String[] {};
			}

		}
		catch (JsonSyntaxException e)
		{
			e.printStackTrace();
			return new String[] {};
		}
	}

	public void addPermission(String user, String permission)
	{
		try
		{
			JsonNode root = Json.json().parse(file_content);

			if (root.has(user))
			{
				JsonNode user_node = root.get(user);
				String[] permissions = user_node.asStringArray();

				if (!ArrayUtils.contains(permissions, permission))
				{
					permissions = ArrayUtils.add(permissions, permission);
					user_node = JsonNode.stringArray(permissions);
					root.set(user, user_node);
					this.file_content = Json.json().serialize(root);
					this.save();
				}
			}
			else
			{
				JsonNode user_node = JsonNode.stringArray(new String[] { permission });
				root.set(user, user_node);
				this.file_content = Json.json().serialize(root);
				this.save();
			}
		}
		catch (JsonSyntaxException e)
		{
			e.printStackTrace();
		}
	}

	public void removePermission(String user, String permission)
	{
		try
		{
			JsonNode root = Json.json().parse(file_content);

			if (root.has(user))
			{
				JsonNode user_node = root.get(user);
				String[] permissions = user_node.asStringArray();

				if (ArrayUtils.contains(permissions, permission))
				{
					permissions = ArrayUtils.remove(permissions, permission);
					user_node = JsonNode.stringArray(permissions);
					root.set(user, user_node);
					this.file_content = Json.json().serialize(root);
					this.save();
				}
			}
			else
			{
				JsonNode user_node = JsonNode.stringArray(new String[] {});
				root.set(user, user_node);
				this.file_content = Json.json().serialize(root);
				this.save();
			}
		}
		catch (JsonSyntaxException e)
		{
			e.printStackTrace();
		}
	}

	private void load()
	{
		try
		{
			file_content = FileUtils.readFile(file);
		}
		catch (IOException e)
		{
			file_content = "{}";
		}
	}

	private void save()
	{
		try
		{
			FileUtils.writeFile(file, file_content);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
